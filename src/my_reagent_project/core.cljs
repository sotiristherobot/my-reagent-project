(ns my-reagent-project.core
    (:require
      [reagent.core :as r]
      [reagent.dom :as d]))

;; -------------------------
;; Views
(def click-count (r/atom 5))

(defn main-header [name]
  [:div
   [:p "Hello, " name "the click count is " @click-count ]])

(defn click-handler []
  (swap! click-count inc))

(defn home-page []
  [:div [:h2 "Welcome to Reagent"]
   [main-header "Sotiris"]
    [:input {:type "button" :value "Click" :on-click  click-handler }]])

;; -------------------------
;; Initialize app

(defn mount-root []
  (d/render [home-page] (.getElementById js/document "app")))

(defn init! []
  (mount-root))
