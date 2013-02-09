(ns punch.test.catalog
  (:use midje.sweet
        punch.catalog))

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
    (contains (resource "Package" "nginx" ["package" "nginx" "node" "default" "class"] {:ensure "installed"})
              (resource "Node" "default" ["node" "default" "class"])))
  (fact edges =>
    (contains {:source "Node[default]", :target "Package[nginx]"}
              {:source "Class[main]",   :target "Node[default]"}))
  (fact nginx-cat => basic-catalog?))
