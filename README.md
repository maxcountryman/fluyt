# Fluyt

*ClojureScript HTTP requests*

**Fluyt is not feature-complete! API subject to change.**

![Dutch fluyt, 1677](https://upload.wikimedia.org/wikipedia/commons/c/c7/Wenceslas_Hollar_-_A_Flute_%28State_2%29.jpg)

## Installation
Fluyt is on Clojars. Add it as a dependency in your `project.clj` like so:

```clojure
:dependencies [[fluyt "0.1.0-SNAPSHOT"] ...]
```

## Usage
Fluyt uses core.async channels to pass around normalized `goog.net.XhrIo`
responses. To use these in a synchronous manner, the requests namespace
provides a `request` wrapper.

Example usage:

```clojure
(ns my-app.core
  (:require [cljs.core.async :refer [<!]]
            [fluyt.requests :as requests])
  (:require-macros [cljs.core.async.macros :refer [go]]))

(enable-console-print!)

;; read the resp off the channel
(go (prn (<! (requests/get "http://httpbin.org/get" {:as :json}))))

;; or pass in a handler fn
(requests/get "http://httpbin.org/get" {:handler prn :as :json})
```

## License
Fluyt is released under the MIT license, see LICENSE for further details.

Copyright © 2014 Max Countryman
