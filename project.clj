(defproject fluyt "0.1.0-SNAPSHOT"
  :description "ClojureScript HTTP requests"
  :url "https://github.com/maxcountryman/fluyt"
  :license {:name "MIT License"
            :url "http://www.opensource.org/licenses/mit-license.php"}

  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/clojurescript "0.0-2173" :scope "provided"]
                 [org.clojure/core.async "0.1.267.0-0d7780-alpha" :scope "provided"]]

  :plugins [[lein-cljsbuild "1.0.2"]]

  :jvm-opts ^:replace ["-Xms512m" "-Xmx512m" "-server"]

  :source-paths ["src"]

  :cljsbuild { 
    :builds [{:id "fluyt"
              :source-paths ["src"]
              :compiler {
                :output-to "fluyt.js"
                :output-dir "out"
                :optimizations :none
                :source-map true}}]})
