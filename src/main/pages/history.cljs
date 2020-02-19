(ns pages.history
  (:require [goog.events]
            [pages.db]
            [re-frame.core :as rf])
  (:import [goog.history Html5History EventType]))

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
    (rf/dispatch [:pages.db/set-page (int page-str)])))

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
