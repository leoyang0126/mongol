package com.leoyang.mongol.rest.storm;

/**
 * Created by yang.liu on 2018/9/5
 */
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.storm.shade.org.json.simple.JSONObject;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;


/**
 * 词频汇总Bolt
 */
public class CountBolt extends BaseRichBolt {
    private HashMap<String, Integer> map = new HashMap<String, Integer>();
    private JmsTemplate jmsTemplate;

    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
                new String[] { "classpath*:spring/jms-*.xml" });
        this.jmsTemplate = (JmsTemplate) applicationContext.getBean("jmsTemplate");
    }

    @Override
    public void execute(Tuple input) {
        String word = input.getStringByField("word");
        Integer count = map.get(word);
        if (count == null) {
            count = 0;
        }
        count++;
        map.put(word, count);

        System.out.println("~~~~~~~~~~~~execute~~~~~~~~~~");
        Set<Map.Entry<String, Integer>> entrySet = map.entrySet();
        for (Map.Entry<String, Integer> entry : entrySet) {
            System.out.println(entry);
        }

        jmsTemplate.send("QUEUE_ItShow", new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(JSONObject.toJSONString(map));
            }
        });
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {

    }

    @Override
    public void cleanup() {
        System.out.println("~~~~~~~~~~~~cleanup~~~~~~~~~~");
        Set<Map.Entry<String, Integer>> entrySet = map.entrySet();
        for (Map.Entry<String, Integer> entry : entrySet) {
            System.out.println(entry);
        }
    }

}