(ns punch.test.handler
  (:use expectations
        ring.mock.request  
        punch.handler)
  (:require [cheshire.core :refer [parse-string]]))


(given (app (request :get "/"))
       (expect
        #(get-in % [:headers "Content-Type"]) "application/json"
        :status    200
        (comp set keys parse-string :body) #{"data" "document_type" "metadata"}
        (comp set keys #(get % "data") parse-string :body) #{"name" "tags" "classes" "edges" "version" "resources"}
        (comp in parse-string :body) {"document_type" "Catalog", "metadata" {"api_version" 1}})
       )
(let [response (app (request :get "/"))
      parsed-json (parse-string (:body response))]
  (expect "application/json" (get-in response [:headers "Content-Type"]))
  (expect 200                (:status response))

  (expect #{"data" "document_type" "metadata"}
          (set (keys parsed-json)))
  (expect #{"name" "tags" "classes" "edges" "version" "resources"}
          (set (keys (get parsed-json "data"))))
  (expect {"document_type" "Catalog", "metadata" {"api_version" 1}}
          (in parsed-json)))

(expect 404
        (:status (app (request :get "/invalid"))))
