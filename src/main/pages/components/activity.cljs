(ns pages.components.activity
  (:require [pages.components.activity-header :refer [activity-header]]
            [pages.components.activity-footer :refer [activity-footer]]
            [pages.history :refer [nav!]]
            [reagent.core :as reagent]))

(defn activity
  [page deleted?]
  (let [opened? (reagent/atom nil)
        on-open #(reset! opened? %)]
    (fn [page deleted?]
      ;; (println "render-activity" page deleted? @opened?)
      [:div.activity
       {:class (if deleted? "deleted")
        :on-click #(reset! opened? nil)}
       [activity-header page @opened? on-open]
       [:div.page (repeat 200 "Content ")]
       [activity-footer page]])))
