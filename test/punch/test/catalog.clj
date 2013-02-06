(ns punch.test.catalog
  (:use expectations
        punch.catalog))

(let [catalog   (catalog-for "localhost")
      resources (:resources catalog)
      edges     (:edges catalog)]
  ;; should have a Class[main]
  (expect {:type     "Class",
           :title    "main",
           :parameters {:name "main"},
           :tags     ["class"],
           :exported false}
          (in resources))
  ;; should have a Stage[main]
  (expect {:type     "Stage",
           :title    "main",
           :parameters {:name "main"},
           :tags     ["stage"],
           :exported false}
          (in resources))
  ;; should have a Class[settings]
  (expect {:type     "Class",
           :title    "Settings",
           :tags     ["class" "settings"],
           :exported false}
          (in resources))
  ;; Stage[main]/Class[main]
  (expect {:source "Stage[main]",
           :target "Class[main]"}
          (in edges))
  ;; Stage[main]/Class[Settings]
  (expect {:source "Stage[main]",
           :target "Class[Settings]"}
          (in edges)))
