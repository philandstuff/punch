(ns punch.test.handler
  (:use expectations
        ring.mock.request  
        punch.handler)
  (:require [cheshire.core :refer [parse-string]]))

(let [resources (get-in (parse-string (catalog-for "localhost")) ["data" "resources"])]
  ;; should have a Class[main]
  (expect {"type"     "Class",
           "title"    "main",
           "parameters" {"name" "main"},
           "tags"     ["class"],
           "exported" false}
          (in resources))
  ;; should have a Stage[main]
  (expect {"type"     "Stage",
           "title"    "main",
           "parameters" {"name" "main"},
           "tags"     ["stage"],
           "exported" false}
          (in resources))
  ;; should have a Class[settings]
  (expect {"type"     "Class",
           "title"    "Settings",
           "tags"     ["class" "settings"],
           "exported" false}
          (in resources)))

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

(expect 404
        (:status (app (request :get "/invalid"))))
