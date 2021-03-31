(ns scramblies-back.domain.scramble)

(defn scramble? [str1 str2]
  (= (frequencies str1)
     (frequencies str2)))

