(ns my-reagent-project.core
    (:require-macros [cljs.core.async.macros :refer [go]])
    (:require
      [reagent.core :as r]
      [reagent.dom :as d]
      [cljs-http.client :as http]
      [cljs.core.async :refer [<!]]
      [my-reagent-project.main_header :as header]
))

;; -------------------------
;; Views
(defn render-posts [posts]
    [:div
     "The posts are ", (str @posts)])

(defn home-page []
  (let [click-count (r/atom 0)
        posts (r/atom ["loading..."])]
    (go (let [response (<! (http/get "https://jsonplaceholder.typicode.com/posts"))]
          ;; (prn (:status response))
          (reset! posts (map :title (:body response)))))
    [:div [:h2 "Welcome to my homepage"]
     [header/header "Sotiris" click-count]
     [render-posts posts]]))

;; -------------------------
;; Initialize app
(defn mount-root []
  (d/render [home-page] (.getElementById js/document "app")))

(defn init! []
  (mount-root))
