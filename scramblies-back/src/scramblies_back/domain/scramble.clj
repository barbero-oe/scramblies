(ns scramblies-back.domain.scramble)

(defn scramble? [str1 target]
  (let [use-character (fn [m k v] (update m k (fnil - 0) v))
        diff (reduce-kv use-character
                        (frequencies str1)
                        (frequencies target))]
    (not-any? neg? (vals diff))))
