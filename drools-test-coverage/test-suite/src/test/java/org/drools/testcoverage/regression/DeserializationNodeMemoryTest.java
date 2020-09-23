package org.drools.testcoverage.regression;

import org.drools.core.impl.KnowledgeBaseFactory;
import org.drools.testcoverage.common.util.KieBaseUtil;
import org.drools.testcoverage.common.util.KieUtil;
import org.junit.Test;
import org.kie.api.KieBase;
import org.kie.api.KieBaseConfiguration;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieModule;
import org.kie.api.marshalling.Marshaller;
import org.kie.api.runtime.KieContainer;
import org.kie.internal.marshalling.MarshallerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class DeserializationNodeMemoryTest {

    private static final String DRL =
            "package fr.sle\n" +
                    "rule \"Accmulate it\"\n" +
    "when\n" +
    "$s : Sensor()\n" +
    "accumulate (Reading($temp : temperature);\n" +
    "$min : min( $temp ),\n" +
    "$max : max( $temp ),\n" +
    "$avg : average( $temp );\n" +
    "$min > 10 \n" +
            ")\n" +
    "then\n" +
        "System.out.println(\"min temperature above 10\");\n"+
    "end";

    @Test
    public void test() throws IOException, ClassNotFoundException {

        KieServices kieServices = KieServices.get();

        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();

        kieFileSystem.write("/fr/sle", DRL);

        KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem);

        KieModule kieModule = kieBuilder.getKieModule();

        KieContainer kieContainer =
                kieServices.newKieContainer( kieModule.getReleaseId() );

        KieBase kieBase = kieContainer.newKieBase(KnowledgeBaseFactory.newKnowledgeBaseConfiguration());

        Marshaller marshaller = MarshallerFactory.newMarshaller(kieBase);

        try(InputStream is = new FileInputStream("/tmp/1600890373613_drools_7.0.0_session")){
            marshaller.unmarshall(is);
        }



    }
}
