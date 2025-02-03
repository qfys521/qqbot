package cn.qfys521.bot.plugin.qfPlugin.config;

import java.text.SimpleDateFormat;
import java.util.concurrent.ConcurrentHashMap;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLOL {
    ConcurrentHashMap<String , Integer> table = new ConcurrentHashMap<>();
    ConcurrentHashMap<String , Long> time = new ConcurrentHashMap<>();

    public int getCount(String userid){
        return table.getOrDefault(userid , 0);
    }
    public void addCount(String userid, int count){
        table.put(userid, getCount(userid) + count);
    }

    public String getTime(String userid){
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time.getOrDefault(userid , 0L));
    }

    public void putTime(String userid, Long time){
        this.time.put(userid, time);
    }
}
