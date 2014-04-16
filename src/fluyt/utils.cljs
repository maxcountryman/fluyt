(ns fluyt.utils)

(defn json-loads
  "Given a string and optionally a flag to convert keys to keywords (default
  true), returns ClojureScript data for the given JSON string."
  [s & [keywordize-keys?]]
  (js->clj (.parse js/JSON s) :keywordize-keys (or keywordize-keys? true)))

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

(defn normalize-resp
  "Given an XhrIo instance, returns a map containing the following keys:

  :status the numeric response status
  :body   the response body
  "
  [resp]
  
  ;; TODO: getResponseHeaders is not found as a method...
  {:status (.getStatus resp) :body (.getResponse resp)})
