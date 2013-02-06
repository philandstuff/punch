(ns punch.test.catalog
  (:use midje.sweet
        punch.catalog))

(def class-main
  {:type     "Class",
   :title    "main",
   :parameters {:name "main"},
   :tags     ["class"],
   :exported false})

(def stage-main
  {:type     "Stage",
   :title    "main",
   :parameters {:name "main"},
   :tags     ["stage"],
   :exported false})

(def class-settings
  {:type     "Class",
   :title    "Settings",
   :tags     ["class" "settings"],
   :exported false})

(def basic-catalog?
  (chatty-checker [actual]
                  (and
                   (clojure.set/subset? #{class-main stage-main class-settings} (:resources actual))
                   (clojure.set/subset? #{{:source "Stage[main]"
                                           :target "Class[main]"}
                                          {:source "Stage[main]"
                                           :target "Class[Settings]"}}
                                        (:edges actual)))))

(fact (catalog-for "localhost") => basic-catalog?)

(let [nginx-cat (catalog-for "nginx")
      {:keys [resources edges]} nginx-cat]
  (fact resources =>
        (contains {:type "Package",
                   :title "nginx",
                   :exported false,
                   :parameters {:ensure "installed"}
                   :tags ["package" "nginx" "node" "default" "class"]}))
  (fact edges =>
        (contains {:source "Node[default]", :target "Package[nginx]"}))
  (fact nginx-cat => basic-catalog?))
