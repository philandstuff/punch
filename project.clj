(defproject punch "0.1.0-SNAPSHOT"
  :description "Tools for generating puppet catalogs"
  :url "https://github.com/philandstuff/punch"
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [compojure "1.1.5"]
                 [cheshire "5.0.1"]
                 [ring-mock "0.1.3"]]
  :plugins [[lein-ring "0.8.2"]
            [lein-expectations "0.0.7"]
            [lein-midje "2.0.4"]]
  :main punch.handler
  :ring {:handler punch.handler/app}
  :profiles {:dev {:dependencies [[expectations "1.4.30"]
                                  [midje "1.4.0"]]}})
