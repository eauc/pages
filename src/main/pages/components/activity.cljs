(ns pages.components.activity
  (:require [cljss.core :refer-macros [defkeyframes]]
            [cljss.reagent :refer-macros [defstyled]]
            [pages.components.activity-header :refer [activity-header]]
            [pages.components.activity-footer :refer [activity-footer]]
            [pages.components.css :as css]
            [reagent.core :as reagent]))

(defstyled container :div
  { ;; position
   :position "absolute"
   :top 0
   :left 0
   ;; box
   :width "100vw"
   :height "100vh"
   ;; grid container layout
   :display "grid"
   :grid-template-rows "3em 1fr"
   :grid-template-areas "\"header\"
                         \"page\""
   :align-items "stretch"
   ;; style
   :background "white"
   ;; animations
   :transition "top 0.4s ease"
   :deleted? {:top "100vh"}})

(defstyled page-container :div
  { ;; grid item layout
   :grid-area "page"
   :align-self "stretch"
   :justify-self "stretch"
   ;; box
   :padding "0.5em 0.5em"
   :overflow-y "auto"})

(defn activity
  [page deleted?]
  (let [opened? (reagent/atom nil)
        on-open #(reset! opened? %)
        animation (str (:slide-up @css/keyframes) " 0.4s ease")]
    (fn [page deleted?]
      ;; (println "render-activity" page deleted? @opened?)
      [container
       {:deleted? deleted?
        :on-click #(reset! opened? nil)
        :style {:animation animation}}
       [activity-header page @opened? on-open]
       [page-container
        (repeat 200 "Content ")]
       (when-not deleted?
         [activity-footer page])])))
