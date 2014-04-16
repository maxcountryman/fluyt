(ns fluyt.connection
  (:require [cljs.core.async :refer [put!]]
            [fluyt.utils :as utils]
            [goog.events :as events])
  (:import [goog.net EventType]
           [goog.net XhrIo]))

;; TODO: this can be required from ClojureScript proper?
(defn xhr-conn
  "Returns a new XhrIo connection."
  []
  (XhrIo.))

(defn xhr
  "Makes an XMLHttpRequest using goog.net.XhrIo. Expects an instance of XhrIo,
  a string-formatted URL, a keyword-formatted method, a map of content (body),
  a map of headers, a core.async response cahnnel and a timeout in
  milliseconds. Returns the response channel."
  [conn url method content headers resp-chan timeout]
  (let [meth    (utils/k-meth->s-meth method)
        content (clj->js content)
        headers (clj->js headers)]

    ;; add an event handler which, upon completion, puts the resp in a channel
    (events/listen conn EventType.COMPLETE
                   (fn xhr-completed-callback [e]
                     (put! resp-chan (utils/normalize-resp (.-target e)))))

    ;; request timeout in milliseconds
    (.setTimeoutInterval conn timeout)

    ;; send off the request
    (.send conn url meth content headers)

    ;; return the response channel to the caller
    resp-chan))
