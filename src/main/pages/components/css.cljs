(ns pages.components.css
  (:require-macros [cljss.core])
  (:require [cljss.core :as css :refer-macros [defkeyframes]]
            [goog.object :as obj]
            ["react" :as react]
            [reagent.core :as r]))

(obj/set js/window "React" react)

(defkeyframes slide-up []
  {"0%" {:opacity 0
         :transform "translateY(100vh)"}
   "100%" {:opacity 1
           :transform "translateY(0)"}})

(def keyframes (r/atom {}))

(defn setup-styles!
  []
  (css/remove-styles!)
  (css/inject-global
    {"*" {:margin 0
          :padding 0
          :border 0
          :font-family "'Roboto', sans-serif"}
     :body {:overflow "hidden"}})
  (reset! keyframes {:slide-up (slide-up)}))
