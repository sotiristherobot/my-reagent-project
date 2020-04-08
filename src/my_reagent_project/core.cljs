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
(def posts (r/atom []))

(defn render-posts []
  [:div
   "The posts are ", (str @posts)])

(defn postHandler [res]
   (reset! posts (js->clj ( res))
 ))

(defn home-page []
  (let [click-count (r/atom 5 )]
    [:div [:h2 "Welcome to Reagent"]
     [header/header "Sotiris" click-count]
     [render-posts posts]]))

;(GET "https://jsonplaceholder.typicode.com/posts" :handler postHandler :response-format {:content-type "json"})
(go (let [response (<! (http/get "https://jsonplaceholder.typicode.com/posts"))]
      (prn (:status response))
      (reset! posts (map :title (:body response)))))
;; -------------------------
;; Initialize app

(defn mount-root []
  (d/render [home-page] (.getElementById js/document "app")))

(defn init! []
  (mount-root))
