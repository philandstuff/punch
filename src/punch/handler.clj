(ns punch.handler
  (:use compojure.core)
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [ring.mock.request :refer [request]]
            [cheshire.core :refer [generate-string]]))

(def blank-catalog
  {:name "foo.vm",
   :tags ["settings"],
   :classes ["settings"],
   :edges [{:target "Class[Settings]",
            :source "Stage[main]"},
           {:target "Class[main]",
            :source "Stage[main]"}],
   :version 1359994915,
   :resources [{:exported false,
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
               ]})

(defn catalog-for [hostname]
  blank-catalog)

(defn to-json-data-map [catalog]
  {:document_type "Catalog"
   :metadata      {:api_version 1}
   :data          catalog})

(defroutes app-routes
  (GET "/" []
       {:headers {"Content-Type" "application/json"}
        :body    (generate-string (to-json-data-map (catalog-for "localhost")))})
  (route/not-found "Not Found"))

(def app
  (handler/site app-routes))

(defn -main [& args]
  (print (:body (app (request :get "/")))))
