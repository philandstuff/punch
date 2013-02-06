(ns punch.catalog)

(def blank-catalog
  {:name "foo.vm",
   :tags ["settings"],
   :classes ["settings"],
   :edges #{{:target "Class[Settings]",
             :source "Stage[main]"},
            {:target "Class[main]",
             :source "Stage[main]"}},
   :version 1359994915,
   :resources #{{:exported false,
                 :tags ["stage"],
                 :title "main",
                 :type "Stage",
                 :parameters {:name "main"}},
                {:exported false,
                 :tags ["class","settings"],
                 :title "Settings",
                 :type "Class"},
                {:exported false,
                 :tags ["class"],
                 :title "main",
                 :type "Class",
                 :parameters {:name "main"}}
                }})

(def nginx-additions
  {:resources #{{:exported false,
                 :title "nginx",
                 :type  "Package",
                 :parameters {:ensure "installed"},
                 :tags ["package" "nginx" "node" "default" "class"]}}
   :edges #{{:source "Node[default]"
             :target "Package[nginx]"}}})

(defn merge-catalogs [c1 c2]
  (let [{:keys [name version]} c2]
    {:name name
     :version version
     ;; :tags not done
     ;; :classes not done
     ;; :edges not done
     :edges (clojure.set/union (:edges c1) (:edges c2))
     :resources (clojure.set/union (:resources c1) (:resources c2))}))

(defn catalog-for [hostname]
  (condp = hostname
    "nginx" (merge-catalogs nginx-additions blank-catalog)
    blank-catalog))
