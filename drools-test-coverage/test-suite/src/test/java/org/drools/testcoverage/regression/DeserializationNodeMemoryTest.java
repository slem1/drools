package org.drools.testcoverage.regression;

import fr.sle.Reading;
import fr.sle.Sensor;
import org.drools.core.impl.KnowledgeBaseFactory;
import org.junit.Test;
import org.kie.api.KieBase;
import org.kie.api.io.ResourceType;
import org.kie.api.marshalling.Marshaller;
import org.kie.api.runtime.KieSession;
import org.kie.internal.marshalling.MarshallerFactory;
import org.kie.internal.utils.KieHelper;

import java.io.*;

public class DeserializationNodeMemoryTest {

    private static final String DRL =
    "package fr.sle\n" +
    "rule \"Accmulate it\"\n" +
    "when\n" +
    "accumulate (Reading($temp : temperature);\n" +
    "$min : min( $temp ),\n" +
    "$max : max( $temp ),\n" +
    "$avg : average( $temp );\n" +
    "$avg > 10 \n" +
            ")\n" +
    "then\n" +
        "System.out.println(\"avg temperature above 10\");\n"+
    "end";

    @Test
    public void test() throws IOException, ClassNotFoundException {

        KieHelper kieHelper = new KieHelper();

        kieHelper.addContent(DRL, ResourceType.DRL);

        KieBase kieBase = kieHelper.build(KnowledgeBaseFactory.newKnowledgeBaseConfiguration());

        KieSession kieSession = kieBase.newKieSession();

        Sensor s = new Sensor("S1");

        kieSession.insert(new Reading(s,13));
        kieSession.insert(new Reading(s,15));

        int i = kieSession.fireAllRules();

        System.out.println("fired rules: " + i);

        Marshaller marshaller = MarshallerFactory.newMarshaller(kieBase);

        byte[] serialized;

        try(ByteArrayOutputStream os  = new ByteArrayOutputStream()){
            marshaller.marshall(os, kieSession);
            serialized = os.toByteArray();
        }

        try(InputStream is = new ByteArrayInputStream(serialized)){
            KieSession unmarshall = marshaller.unmarshall(is);
            int i1 = unmarshall.fireAllRules();
            System.out.println("fired rules after deserialization: " + i1);
        }
    }

    @Test
    public void test2() throws IOException, ClassNotFoundException {

        KieHelper kieHelper = new KieHelper();

        kieHelper.addContent(DRL, ResourceType.DRL);

        KieBase kieBase = kieHelper.build(KnowledgeBaseFactory.newKnowledgeBaseConfiguration());

        KieSession kieSession = kieBase.newKieSession();

        Sensor s = new Sensor("S1");

        kieSession.insert(new Reading(s,13));
        kieSession.insert(new Reading(s,15));

        int i = kieSession.fireAllRules();

        System.out.println("fired rules: " + i);

        Marshaller marshaller = MarshallerFactory.newMarshaller(kieBase);

        byte[] serialized;

        try(InputStream is = new FileInputStream("/tmp/1600974120125_drools_7.0.0_session")){
            KieSession unmarshall = marshaller.unmarshall(is);
            int i1 = unmarshall.fireAllRules();
            System.out.println("fired rules after deserialization: " + i1);
        }
    }
}
