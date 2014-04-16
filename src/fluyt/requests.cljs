(ns fluyt.requests
  (:refer-clojure :exclude [get])
  (:require [cljs.core.async :refer [<! chan]]
            [fluyt.connection :refer [xhr xhr-conn]]
            [fluyt.utils :as utils])
  (:require-macros [cljs.core.async.macros :refer [go]]))

(defn- coerce-json-body
  "Given a response coerces a response from a JSON string to Clojure."
  [resp]
  (update-in resp [:body] utils/json-loads))


;; Response body coercion, e.g. JSON
(defmulti coerce-response-body (fn [req _] (:as req)))

(defmethod coerce-response-body :json
  [req resp]
  (coerce-json-body resp))

(defmethod coerce-response-body :default
  [req resp]
  resp)


;; Request wrapper
(defn request
  "Given a map of keys, calls xhr. Returns the response channel.

  Expects the following required keys:

  :method the request method as a keyword, e.g. :get
  :url    the URL to request as a string

  Optional keys:

  :body      request body as a map
  :headers   request headers as a map
  :resp-chan the core.async/chan to put normalized responses on
  :timeout   request timeout in milliseconds
  :handler   the handler fn called over the normalized request response"
  [{:keys [method url body headers resp-chan timeout handler] :as req}]
  (let [conn      (xhr-conn)  ;; TODO: use a connection pool; reuse XhrIo?
        handler   (or handler identity)
        timeout   (or timeout 2000)
        resp-chan (or resp-chan (chan))]
    (go (let [resp (<! (xhr conn url method body headers resp-chan timeout))]
          (handler (coerce-response-body req resp))))))

(defn get
  "Like request, but supplies the :get method by default. Expects a
  string-formatted URL."
  [url & [req]]
  (request (merge req {:method :get :url url})))

(defn head
  "Like request, but supplies the :head method by default. Expects a
  string-formatted URL."
  [url & [req]]
  (request (merge req {:method :head :url url})))

(defn post
  "Like request, but supplies the :post method by default. Expects a
  string-formatted URL."
  [url & [req]]
  (request (merge req {:method :post :url url})))

(defn put
  "Like request, but supplies the :put method by default. Expects a
  string-formatted URL."
  [url & [req]]
  (request (merge req {:method :put :url url})))

(defn delete
  "Like request, but supplies the :delete method by default. Expects a
  string-formatted URL."
  [url & [req]]
  (request (merge req {:method :delete :url url})))

(defn delete
  "Like request, but supplies the :delete method by default. Expects a
  string-formatted URL."
  [url & [req]]
  (request (merge req {:method :delete :url url})))

(defn trace
  "Like request, but supplies the :trace method by default. Expects a
  string-formatted URL."
  [url & [req]]
  (request (merge req {:method :trace :url url})))

(defn options
  "Like request, but supplies the :options method by default. Expects a
  string-formatted URL."
  [url & [req]]
  (request (merge req {:method :options :url url})))

(defn patch
  "Like request, but supplies the :patch method by default. Expects a
  string-formatted URL."
  [url & [req]]
  (request (merge req {:method :patch :url url})))
