(ns my-reagent-project.core
  (:import goog.history.Html5History)
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require
    [reagent.core :as r]
    [reagent.dom :as d]
    [cljs-http.client :as http]
    [cljs.core.async :refer [<!]]
    [my-reagent-project.main_header :as header]
    [clojure.string :as str]
    [secretary.core :as secretary]
    [goog.events :as events]
    [goog.history.EventType :as EventType]
    ))

(def app-state (r/atom {}))

(defn hook-browser-navigation! []
  (doto (Html5History.)
    (events/listen
      EventType/NAVIGATE
      (fn [event]
        (secretary/dispatch! (.-token event))))
    (.setEnabled true)))

(defn app-routes []
  (secretary/set-config! :prefix "#")
  (secretary/defroute "/" [] (swap! app-state assoc :page :home))
  (secretary/defroute "/about" [] (swap! app-state assoc :page :about))

  (hook-browser-navigation!))

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
          (reset! posts
                  (map
                    (fn [response-body]
                      (str/capitalize (:title response-body)))
                    (:body response)))))
    [:div [:h2 "Welcome to my homepage"]
     [header/header "Sotiris" click-count]
     [render-posts posts]]))

;; Routes
(defn about-page []
  [:div [:h1 "About Page"]
   [:a {:href "#/about"} "about page"]])



(defmulti current-page #(@app-state :page))
(defmethod current-page :home []
  [home-page])
(defmethod current-page :about []
  [about-page])
(defmethod current-page :default []
  [:div [:h1 "Page not found"]])


;; -------------------------
;; Initialize app
(defn mount-root []
  (app-routes)
  (d/render [current-page] (.getElementById js/document "app")))

(defn init! []
  (mount-root))
