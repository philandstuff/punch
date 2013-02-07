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
                   (contains? (:resources actual) (resource "Stage" "main" ["stage"] {:name "main"}))
                   (contains? (:resources actual) (resource "Class" "main" ["class"] {:name "main"}))
                   (contains? (:resources actual) (resource "Class" "Settings" ["class" "settings"]))
                   (contains? (:edges actual) {:source "Stage[main]" :target "Class[main]"})
                   (contains? (:edges actual) {:source "Stage[main]" :target "Class[Settings]"}))))

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
