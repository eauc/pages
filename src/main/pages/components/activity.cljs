(ns pages.components.activity
  (:require [cljs-styled-components.reagent :refer [clj-props defkeyframes defstyled]]
            [pages.components.activity-header :refer [activity-header]]
            [pages.components.activity-footer :refer [activity-footer]]
            [pages.history :refer [nav!]]
            [reagent.core :as reagent]))

(defkeyframes
  slide-up
  {"0%" {:opacity 0
         :transform "translateY(100vh)"}
   "100%" {:opacity 1
           :transform "translateY(0)"}})

(defstyled page :div
  {;; grid item layout
   :grid-area "page"
   :align-self "stretch"
   :justify-self "stretch"
   ;; box
   :padding "0.5em 0.5em"
   :overflow-y "auto"})

(def page* (:react-component (meta page)))

(defstyled container :div
  { ;; position
   :position "absolute"
   :top 0
   "&.deleted" {:top "100vh"}
   :left 0
   ;; box
   :width "100vw"
   :height "100vh"
   ;; grid container layout
   :display "grid"
   :grid-template-rows "3em 1fr"
   :grid-template-areas
   "\"header\"
    \"page\""
   :align-items "stretch"
   ;; style
   :background "white"
   ;; animations
   :animation (slide-up "0.4s ease")
   :transition "top 0.4s ease"})

(def container* (:react-component (meta container)))

(defn activity
  [page deleted?]
  (let [opened? (reagent/atom nil)
        on-open #(reset! opened? %)]
    (fn [page deleted?]
      (println "render-activity" page deleted? @opened?)
      [:> container*
       {:class (if deleted? "deleted")
        :on-click #(reset! opened? nil)}
       [activity-header page @opened? on-open]
       [:> page*
        (repeat 200 "Content ")]
       (when-not deleted?
         [activity-footer page])])))
