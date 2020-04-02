(ns my-reagent-project.core
    (:require
      [reagent.core :as r]
      [reagent.dom :as d]
      [ajax.core :refer [GET]]))

;; -------------------------
;; Views
(def click-count (r/atom 5))
(def posts (r/atom []))

(defn main-header [name]
  [:div
   [:p "Hello, " name "the click count is " @click-count ]])

(defn click-handler []
  (swap! click-count inc))

(defn render-posts []
  [:div
   "The posts are ", (str @posts)])

(defn postHandler [res]
   (reset! posts (js->clj res)


     ))

(GET "https://jsonplaceholder.typicode.com/posts" :handler postHandler)

(defn home-page []
  [:div [:h2 "Welcome to Reagent"]
   [main-header "Sotiris"]
    [:input {:type "button" :value "Click" :on-click  click-handler }]
    [render-posts posts]
   ])

;; -------------------------
;; Initialize app

(defn mount-root []
  (d/render [home-page] (.getElementById js/document "app")))

(defn init! []
  (mount-root))
