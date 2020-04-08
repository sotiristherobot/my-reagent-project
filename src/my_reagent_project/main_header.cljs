(ns my-reagent-project.main_header)

(defn header [name click-count]
  (letfn [(click-handler [] (swap! click-count inc))]
    [:div
     [:input {:type "button" :value "Click" :on-click click-handler}]
     [:p "Hello, " name " the click count is " @click-count ]]))