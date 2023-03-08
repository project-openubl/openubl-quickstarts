package io.github.project.openubl.quickstart.xbuilder.springboot;

import io.github.project.openubl.xsender.camel.routes.CxfEndpointConfiguration;
import io.github.project.openubl.xsender.camel.routes.SunatRouteBuilder;
import org.apache.camel.component.cxf.jaxws.CxfEndpoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class XSenderSpringSetup {

    @Bean
    public SunatRouteBuilder produceSunatRouteBuilder() {
        return new SunatRouteBuilder();
    }

    @Bean(name = "cxfBillServiceEndpoint")
    CxfEndpoint produceCxfBillServiceEndpoint() {
        return new CxfEndpointConfiguration().cxfBillServiceEndpoint(false);
    }

    @Bean(name = "cxfBillConsultServiceEndpoint")
    CxfEndpoint produceCxfBillConsultServiceEndpoint() {
        return new CxfEndpointConfiguration().cxfBillConsultServiceEndpoint(false);
    }

    @Bean(name = "cxfBillValidServiceEndpoint")
    CxfEndpoint produceCxfBillValidServiceEndpoint() {
        return new CxfEndpointConfiguration().cxfBillValidServiceEndpoint(false);
    }

}
