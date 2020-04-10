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
    [reitit.frontend :as rf]
    [reitit.frontend.easy :as rfe]
    [reitit.frontend.controllers :as rfc]
    [fipp.edn :as fedn]
    [reitit.coercion.spec :as rss]
    ))

(defonce match (r/atom nil))

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

(defn current-page []
  [:div
   [:ul
    [:li [:a {:href (rfe/href ::home-page)} "Frontpage"]]
    [:li [:a {:href (rfe/href ::about-page)} "About"]]
    ]
   (if @match
     (let [view (:view (:data @match))]
       [view @match]))
   [:pre (with-out-str (fedn/pprint @match))]])

;; Routes
(defn about-page []
  [:div [:h1 "About Page"]])

;; -------------------------
;; Initialize app
(def routes
  [["/"
    {:name ::home-page
     :view home-page}]

   ["/about"
    {:name ::about-page
     :view about-page}]])

(defn init! []
  (rfe/start!
    (rf/router routes {:data {:coercion rss/coercion}})
    (fn [m] (reset! match m))
    ;; set to false to enable HistoryAPI
    {:use-fragment false})
  (d/render [current-page] (.getElementById js/document "app")))
(init!)
