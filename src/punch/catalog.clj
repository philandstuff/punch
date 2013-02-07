(ns punch.catalog)

(defn resource
  ([type title tags]
     (resource type title tags nil))
  ([type title tags params]
     (let [res {:type type
                :title title
                :tags tags
                :exported false}] ;; hardcoded for now
       (if params
         (assoc res :parameters params)
         res))))

(def blank-catalog
  {:name "foo.vm",
   :tags ["settings"],
   :classes ["settings"],
   :edges #{{:target "Class[Settings]",
             :source "Stage[main]"},
            {:target "Class[main]",
             :source "Stage[main]"}},
   :version 1359994915,
   :resources #{(resource "Stage" "main" ["stage"] {:name "main"})
                (resource "Class" "Settings" ["class" "settings"] nil)
                (resource "Class" "main" ["class"] {:name "main"})
                }})

(def nginx-additions
  {:resources #{(resource "Package" "nginx" ["package" "nginx" "node" "default" "class"] {:ensure "installed"})
                (resource "Node"  "default" ["node" "default" "class"])}
   :edges #{{:source "Node[default]" :target "Package[nginx]"}
            {:source "Class[main]"   :target "Node[default]"}}})

(defn merge-catalogs [c1 c2]
  (let [{:keys [name version]} c2]
    {:name name
     :version version
     ;; :tags not done
     ;; :classes not done
     :edges (clojure.set/union (:edges c1) (:edges c2))
     :resources (clojure.set/union (:resources c1) (:resources c2))}))

(defn catalog-for [hostname]
  (condp = hostname
    "nginx" (merge-catalogs nginx-additions blank-catalog)
    blank-catalog))
