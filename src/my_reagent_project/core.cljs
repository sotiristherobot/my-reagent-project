(ns my-reagent-project.core
    (:require
      [reagent.core :as r]
      [reagent.dom :as d]))

;; -------------------------
;; Views

(defn main-header [name]
  [:div
   [:p "Hello, " name]])


(defn click-handler []
  (js/console.log "Hello"))

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
