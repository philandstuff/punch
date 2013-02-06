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
                   (some #{class-main} (:resources actual))
                   (some #{stage-main} (:resources actual))
                   (some #{class-settings} (:resources actual))
                   (some #{{:source "Stage[main]"
                            :target "Class[main]"}} (:edges actual))
                   (some #{{:source "Stage[main]"
                            :target "Class[Settings]"}} (:edges actual)))))

(fact (catalog-for "localhost") => basic-catalog?)

(let [nginx-cat (catalog-for "nginx")
      {:keys [resources edges]} nginx-cat]
  (fact resources =>
        (contains {:type "Package",
                   :title "nginx",
                   :exported false,
                   :parameters {:ensure "installed"}
                   :tags ["package" "nginx" "node" "default" "class"]})))
