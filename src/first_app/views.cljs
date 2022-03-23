(ns first-app.views
  (:require
   [re-frame.core :as re-frame]
   [first-app.events :as events]
   [first-app.subs :as subs]))

(defn text-input [id]
  (let [value (re-frame/subscribe [::subs/text-subs id])]
    [:div.field.has-addons
     [:div.control
      [:input {:value @value
               :on-change #(re-frame/dispatch [::events/update-text id (-> % .-target .-value)])
               :type "text" :placeholder "Text Input"}]
      [:button {:on-click #(re-frame/dispatch [::events/insert-text])} "Insert"]]]))

(defn text-list []
  (let [text @(re-frame/subscribe [::subs/list-subs])]
    [:div
     [:ul (map (fn [s] [:li (str s)]) text)]]))

(defn main-panel []
  (let [name (re-frame/subscribe [::subs/name])]
    [:div.section
     [text-input :text-input]
     [text-list]
     ]))
