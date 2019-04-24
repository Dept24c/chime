(ns chime.time
  (:import (java.time Instant Period Duration)))

(set! *warn-on-reflection* true)


(defn ^Instant now []
  (Instant/now))

(defn isBefore? [^Instant t1 ^Duration t2]
  (.isBefore t1 t2))

(defn nanos
  [n]
  (Duration/ofNanos n))

(defn millis
  [n]
  (Duration/ofMillis n))

(defn seconds
  [n]
  (Duration/ofSeconds n))

(defn minutes
  [n]
  (Duration/ofMinutes n))

(defn hours
  [n]
  (Duration/ofHours n))

(defn days
  [n]
  (Duration/ofDays n))

(defn from-now
  [^Duration d]
  (.plus (now) d))

(defn ago
  [^Duration d]
  (.minus (now) d))

(defn periodic-seq
  "Returns a sequence of date-time values growing over specific period.
    The 2 argument function takes as input the starting value and the growing value,
    returning a lazy infinite sequence.
    The 3 argument function takes as input the starting value, the upper bound value,
    and the growing value, return a lazy sequence."
  ([^Instant start ^Duration amount]
   (map (fn [i]
          (.addTo (.multipliedBy amount (int i)) ^Instant start))
        (iterate inc 0)))
  ([^Instant start ^Instant end ^Duration amount]
   (take-while (fn [^Instant next]
                 (isBefore? next end))
               (periodic-seq start amount))))

(defn ms-between [^Instant start ^Instant end]
  (if (.isBefore end start)
    0
    (-> (java.time.Duration/between start end) .toMillis)))
