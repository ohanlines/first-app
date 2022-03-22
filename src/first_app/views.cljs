(ns first-app.views
  (:require
   [re-frame.core :as re-frame]
   [first-app.subs :as subs]
   ))

(defn text-input []
  [:div.field.has-addons
   [:div.control
    [:input {:type "text" :placeholder "Text Input"}]]
   [:div.control
    [:button "Insert"]]])

(defn main-panel []
  (let [name (re-frame/subscribe [::subs/name])]
    [:div.section
     [text-input]
     ]))
