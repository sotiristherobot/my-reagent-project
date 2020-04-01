(ns my-reagent-project.core
    (:require
      [reagent.core :as r]
      [reagent.dom :as d]))

;; -------------------------
;; Views

(defn main-header []
  [:div
   [:p "I include simple-component."]
   ])

(defn home-page []
  [:div [:h2 "Welcome to Reagent"]
   [main-header]
   ])

;; -------------------------
;; Initialize app

(defn mount-root []
  (d/render [home-page] (.getElementById js/document "app")))

(defn init! []
  (mount-root))
