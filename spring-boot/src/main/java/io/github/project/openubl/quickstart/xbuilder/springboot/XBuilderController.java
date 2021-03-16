package io.github.project.openubl.quickstart.xbuilder.springboot;

import io.github.project.openubl.quickstart.xbuilder.springboot.xbuilder.XBuilderConfig;
import io.github.project.openubl.quickstart.xbuilder.springboot.xbuilder.XBuilderSystemClock;
import io.github.project.openubl.xmlbuilderlib.facade.DocumentManager;
import io.github.project.openubl.xmlbuilderlib.facade.DocumentWrapper;
import io.github.project.openubl.xmlbuilderlib.models.catalogs.Catalog6;
import io.github.project.openubl.xmlbuilderlib.models.input.common.ClienteInputModel;
import io.github.project.openubl.xmlbuilderlib.models.input.common.ProveedorInputModel;
import io.github.project.openubl.xmlbuilderlib.models.input.standard.DocumentLineInputModel;
import io.github.project.openubl.xmlbuilderlib.models.input.standard.invoice.InvoiceInputModel;
import io.github.project.openubl.xmlbuilderlib.models.output.standard.invoice.InvoiceOutputModel;
import io.github.project.openubl.xmlbuilderlib.utils.CertificateDetails;
import io.github.project.openubl.xmlbuilderlib.utils.CertificateDetailsFactory;
import io.github.project.openubl.xmlbuilderlib.xml.XMLSigner;
import io.github.project.openubl.xmlbuilderlib.xml.XmlSignatureHelper;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.w3c.dom.Document;

import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.cert.X509Certificate;
import java.util.Arrays;

@RestController
public class XBuilderController {

    @RequestMapping(
            method = RequestMethod.POST,
            value = "/api/create-xml",
            produces = "text/plain"
    )
    public String createXML(@RequestBody String clientName) throws Exception {
        InvoiceInputModel input = createInvoice(clientName);
        DocumentWrapper<InvoiceOutputModel> result = DocumentManager.createXML(input, new XBuilderConfig(), new XBuilderSystemClock());
        String xml = result.getXml();

        // Sign XML
        InputStream ksInputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("LLAMA-PE-CERTIFICADO-DEMO-12345678912.pfx");
        CertificateDetails certificate = CertificateDetailsFactory.create(ksInputStream, "password");

        X509Certificate x509Certificate = certificate.getX509Certificate();
        PrivateKey privateKey = certificate.getPrivateKey();
        Document signedXML = XMLSigner.signXML(xml, "Project OpenUBL", x509Certificate, privateKey);

        //  Return
        byte[] bytesFromDocument = XmlSignatureHelper.getBytesFromDocument(signedXML);
        return new String(bytesFromDocument, StandardCharsets.ISO_8859_1);
    }

    private InvoiceInputModel createInvoice(String clientName) {
        return InvoiceInputModel.Builder.anInvoiceInputModel()
                .withSerie("F001")
                .withNumero(1)
                .withProveedor(ProveedorInputModel.Builder.aProveedorInputModel()
                        .withRuc("12345678912")
                        .withRazonSocial("Los grandes S.A.C.")
                        .build()
                )
                .withCliente(ClienteInputModel.Builder.aClienteInputModel()
                        .withNombre(clientName)
                        .withNumeroDocumentoIdentidad("12121212121")
                        .withTipoDocumentoIdentidad(Catalog6.RUC.toString())
                        .build()
                )
                .withDetalle(Arrays.asList(
                        DocumentLineInputModel.Builder.aDocumentLineInputModel()
                                .withDescripcion("Item1")
                                .withCantidad(new BigDecimal(10))
                                .withPrecioUnitario(new BigDecimal(100))
                                .build(),
                        DocumentLineInputModel.Builder.aDocumentLineInputModel()
                                .withDescripcion("Item2")
                                .withCantidad(new BigDecimal(10))
                                .withPrecioUnitario(new BigDecimal(100))
                                .build())
                )
                .build();
    }

}
