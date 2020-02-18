(ns pages.app
  (:require [clojure.edn :as edn]
            [goog.events]
            [reagent.core :as reagent]
            [re-frame.core :as rf])
  (:import [goog.history Html5History EventType]))

(enable-console-print!)

(println "Coucouc CLJS")

(js/console.log "Coucouc JS")

(def save-state
  (re-frame.core/->interceptor
    :id      ::save-state
    :after  (fn [{{:keys [db]} :effects :as context}]
              (.setItem js/localStorage "app-state" (pr-str db))
              context)))

(rf/reg-event-db
  ::init
  (fn [db [_ state]]
    state))

(def load-state
  (rf/dispatch
    [::init
     (edn/read-string
       (or
         (.getItem js/localStorage "app-state")
         "{}"))]))

(rf/reg-event-db
  ::set-page
  [save-state]
  (fn [db [_ page]]
    (assoc db ::page page)))

(rf/reg-sub
  ::page
  (fn [db _]
    (::page db 0)))

(defn get-token []
  (str js/window.location.pathname js/window.location.search))

(defn make-history []
  (doto (Html5History.)
    (.setPathPrefix (str js/window.location.protocol
                         "//"
                         js/window.location.host))
    (.setUseFragment false)))

(defn handle-url-change [e]
  ;; log the event object to console for inspection
  (js/console.log e)
  ;; and let's see the token
  (js/console.log (str "Navigating: " (get-token)))
  ;; we are checking if this event is due to user action,
  ;; such as click a link, a back button, etc.
  ;; as opposed to programmatically setting the URL with the API
  (when-not (.-isNavigation e)
    ;; in this case, we're setting it
    (js/console.log "Token set programmatically")
    ;; let's scroll to the top to simulate a navigation
    (js/window.scrollTo 0 0))
  ;; dispatch on the token
  (let [[_ _ page-str] (clojure.string/split (get-token) #"/")]
    (rf/dispatch [::set-page (int page-str)])))

(defonce history
  (doto (make-history)
    (goog.events/listen EventType.NAVIGATE
                        ;; wrap in a fn to allow live reloading
                        #(handle-url-change %))
    (.setEnabled true)))

(defn nav! [token]
  (.setToken history token))

(defn back-page!
  []
  (.back js/history))

(defn next-page!
  [page]
  (set! (.-location js/document) ))

(defn greet
  []
  [:div.activity
   [:div.header
    [:button.back
     {:on-click back-page!}
     "<"]
    (str "Page " @(rf/subscribe [::page]))]
   [:div.page (repeat 200 "Content ")]
   [:div.footer
    [:button.action
     {:on-click #(nav! (str "/page/" (inc @(rf/subscribe [::page]))))}
     ">"]]])

(reagent/render
  [greet]
  (js/document.getElementById "app"))
