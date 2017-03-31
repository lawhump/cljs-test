(set-env!
 :source-paths #{"src/cljs"}
 :resource-paths #{"static"}

 :dependencies '[
                 [org.clojure/clojure "1.7.0"]
                 [org.clojure/clojurescript "1.7.170"]
                 [adzerk/boot-cljs "1.7.170-3"]
                 [pandeiro/boot-http "0.7.0"]
                 [adzerk/boot-reload "0.4.9"]
                 [adzerk/boot-cljs-repl "0.3.0"]
                 [com.cemerick/piggieback "0.2.1"]     ;; needed by bREPL
                 [weasel "0.7.0"]                      ;; needed by bREPL
                 [org.clojure/tools.nrepl "0.2.12"]    ;; needed by bREPL
                 [deraen/boot-sass "0.3.0"]
                 [org.slf4j/slf4j-nop "1.7.22"]
                 ])

(require '[adzerk.boot-cljs :refer [cljs]]
         '[pandeiro.boot-http :refer [serve]]
         '[adzerk.boot-reload :refer [reload]]
         '[deraen.boot-sass :refer [sass]]
         '[adzerk.boot-cljs-repl :refer [cljs-repl start-repl]])

;; define dev task as composition of subtasks
(deftask dev
  "Launch Immediate Feedback Development Environment"
  []
  (comp
    (serve :dir "target")
    (watch)
    (reload)
    (cljs-repl) ;; before cljs task
    (cljs :optimizations :none)
    (sass "static/styles")
    (target :dir #{"target"})))
