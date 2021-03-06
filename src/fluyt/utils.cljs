(ns fluyt.utils
  (:require [clojure.string :as string]))

(defn json-loads
  "Given a string and optionally a flag to convert keys to keywords (default
  true), returns ClojureScript data for the given JSON string."
  [s & [keywordize-keys?]]
  (try
    (let [json (.parse js/JSON s)]
      (js->clj json :keywordize-keys (or keywordize-keys? true)))

    ;; in cases where we are passed an improperly formatted string, return the
    ;; string back to the caller
    (catch js/SyntaxError _ s)))

(defn k-meth->s-meth
  "Given a valid keyword, returns its string value; nil otherwise."
  [k]
  (let [meths {:get     "GET"
               :head    "HEAD"
               :post    "POST"
               :put     "PUT"
               :delete  "DELETE"
               :trace   "TRACE"
               :options "OPTIONS"
               :patch   "PATCH"}]
    (k meths)))

(defn parse-headers
  "Given a string of colon-delineated, carriage-return-line-feed terminated
  headers, returns a map of headers."
  [s]
  (into {} (map (fn [s'] (string/split s' #": ")) (string/split s #"\r\n"))))

(defn normalize-resp
  "Given an XhrIo instance, returns a map containing the following keys:

  :status the numeric response status
  :body   the response body
  "
  [resp]
  ;; TODO: getResponseHeaders is not found as a method...
  {:status  (.getStatus resp)
   :headers (-> resp .getAllResponseHeaders parse-headers)
   :body    (.getResponse resp)})
