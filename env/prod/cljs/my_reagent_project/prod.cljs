(ns my-reagent-project.prod
  (:require
    [my-reagent-project.core :as core]))

;;ignore println statements in prod
(set! *print-fn* (fn [& _]))

(core/init!)
