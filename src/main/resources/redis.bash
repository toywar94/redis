#score order desc
ZREVRANGE {key} {start} {end}
# value cnt
ZCARD {KEY}
# value delete
DEL {key}

ZADD key score userId