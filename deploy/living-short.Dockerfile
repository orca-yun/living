FROM dragonwell-registry.cn-hangzhou.cr.aliyuncs.com/dragonwell/dragonwell:17
ENV ENV 'test'
ADD living-short.jar /
EXPOSE 8004
ENTRYPOINT exec java \
    --add-opens java.base/sun.net=ALL-UNNAMED \
    -server \
    -Dproject.name=live-short \
    -Duser.timezone=Asia/Shanghai \
    -jar -Dspring.profiles.active=${ENV} living-short.jar
