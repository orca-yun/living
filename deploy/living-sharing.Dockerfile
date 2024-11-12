FROM dragonwell-registry.cn-hangzhou.cr.aliyuncs.com/dragonwell/dragonwell:17
ENV ENV 'test'
ADD living-sharing.jar /
EXPOSE 8003
ENTRYPOINT exec java \
    --add-opens java.base/sun.net=ALL-UNNAMED \
    -server \
    -Dproject.name=live-sharing \
    -Duser.timezone=Asia/Shanghai \
    -jar -Dspring.profiles.active=${ENV} living-sharing.jar
