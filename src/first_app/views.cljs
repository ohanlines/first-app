(ns first-app.views
  (:require
   [re-frame.core :as re-frame]
   [first-app.events :as events]
   [first-app.subs :as subs]))

(defn text-input [id]
  (let [value (re-frame/subscribe [::subs/text-subs id])]
    [:div.field.is-grouped
     [:div.control
      [:input {:value       @value
               :type        "text"
               :placeholder "Text Input"
               :on-change   #(re-frame/dispatch [::events/update-text id (-> % .-target .-value)])}]]
     [:div.control
      [:button {:disabled   (zero? (count @value))
                :on-click   #(do (re-frame/dispatch [::events/insert-text])
                                 (re-frame/dispatch [::events/dissoc-text]))} "Insert"]
      [:button {:on-click   #(re-frame/dispatch [::events/clear-storage])} "Clear"]]]))

(defn main-panel []
  (let [list  @(re-frame/subscribe [::subs/list-subs])]
    [:div.section
     [text-input :text-staging]
     (map #(vector :p %) list)
     ]))
