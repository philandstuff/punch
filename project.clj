(defproject punch "0.1.0-SNAPSHOT"
  :description "Tools for generating puppet catalogs"
  :url "https://github.com/philandstuff/punch"
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [compojure "1.1.5"]]
  :plugins [[lein-ring "0.8.2"]]
  :ring {:handler punch.handler/app}
  :profiles
  {:dev {:dependencies [[ring-mock "0.1.3"]]}})
