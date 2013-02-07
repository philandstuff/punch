(ns punch.test.handler
  (:use midje.sweet
        ring.mock.request  
        punch.handler)
  (:require [cheshire.core :refer [parse-string]]))

(fact
  (app (request :get "/invalid")) => (contains {:status 404}))

(let [response (app (request :get "/"))
      parsed-json (parse-string (:body response))]
  (facts "basic catalog response"
    (fact response            => (contains {:status 200}))
    (fact (:headers response) => (contains {"Content-Type" "application/json"}))
    (fact parsed-json         => (contains {"document_type" "Catalog", "metadata" {"api_version" 1}}))
    (fact (keys parsed-json)  => (contains "data" "document_type" "metadata" :in-any-order))
    (fact (keys (get parsed-json "data"))
      => (contains "name" "tags" "classes" "edges" "version" "resources" :in-any-order))))

