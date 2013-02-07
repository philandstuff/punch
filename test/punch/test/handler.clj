(ns punch.test.handler
  (:use [midje.sweet :only [fact contains]]
        expectations
        ring.mock.request  
        punch.handler)
  (:require [cheshire.core :refer [parse-string]]))

(fact
  (app (request :get "/invalid")) => (contains {:status 404}))

(let [response (app (request :get "/"))
      parsed-json (parse-string (:body response))]
  (expect "application/json" (get-in response [:headers "Content-Type"]))
  (expect 200                (:status response))

  (expect {"document_type" "Catalog", "metadata" {"api_version" 1}}
          (in parsed-json))
  (expect #{"data" "document_type" "metadata"}
          (set (keys parsed-json)))
  (expect #{"name" "tags" "classes" "edges" "version" "resources"}
          (set (keys (get parsed-json "data")))))

