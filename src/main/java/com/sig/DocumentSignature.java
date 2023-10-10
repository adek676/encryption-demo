package com.sig;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javax.xml.crypto.MarshalException;
import javax.xml.crypto.dsig.*;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import javax.xml.crypto.dsig.keyinfo.KeyValue;
import javax.xml.crypto.dsig.keyinfo.X509Data;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
import javax.xml.crypto.dsig.spec.TransformParameterSpec;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.Collections;
import java.util.List;

public class DocumentSignature {
    private XMLSignatureFactory xmlSignatureFactory = XMLSignatureFactory.getInstance("DOM");
    private KeyPair keyPair;

    public DocumentSignature(KeyPair keyPair) {
        this.keyPair = keyPair;
    }

    public void signDocument(Document xmlDoc) {
        XMLSignature signature = xmlSignatureFactory.newXMLSignature(createSignedInfo(), createKeyInfoKeyValue());
        sign(signature, signContext(xmlDoc.getDocumentElement()));
    }

    private void sign(XMLSignature signature, XMLSignContext signContext) {
        try {
            signature.sign(signContext);
        } catch (MarshalException | XMLSignatureException e) {
            throw new RuntimeException(e);
        }
    }

    private XMLSignContext signContext(Node nodeToSign) {
        return new DOMSignContext(keyPair.getPrivate(), nodeToSign);
    }

    private KeyInfo createKeyInfoKeyValue() {
        KeyInfoFactory keyInfoFactory = xmlSignatureFactory.getKeyInfoFactory();
        try {
            KeyValue kv = keyInfoFactory.newKeyValue(keyPair.getPublic());
            return keyInfoFactory.newKeyInfo(List.of(kv));
        } catch (KeyException e) {
            throw new RuntimeException(e);
        }
    }

    private KeyInfo createKeyInfo(X509Certificate cert) {
        KeyInfoFactory keyInfoFactory = xmlSignatureFactory.getKeyInfoFactory();
        try {
            X509Data x509Data = keyInfoFactory.newX509Data(Collections.singletonList(cert.getEncoded()));
            return keyInfoFactory.newKeyInfo(Collections.singletonList(x509Data));
        } catch (CertificateEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    private SignedInfo createSignedInfo() {
        try {
            CanonicalizationMethod canonicalizationMethod =
                    xmlSignatureFactory.newCanonicalizationMethod(CanonicalizationMethod.EXCLUSIVE, (C14NMethodParameterSpec) null);
            SignatureMethod signatureMethod =
                    xmlSignatureFactory.newSignatureMethod(SignatureMethod.RSA_SHA256, null);
            DigestMethod digestMethod = xmlSignatureFactory.newDigestMethod(DigestMethod.SHA256, null);
            Transform transformEnvelopedSignature = xmlSignatureFactory.newTransform(Transform.ENVELOPED, (TransformParameterSpec) null);
            Transform transformC14NCanonicalization = xmlSignatureFactory.newTransform(CanonicalizationMethod.EXCLUSIVE, (TransformParameterSpec) null);
            List<Transform> transformList = List.of(transformEnvelopedSignature, transformC14NCanonicalization);
            Reference reference = xmlSignatureFactory.newReference("", digestMethod, transformList, null, null);
            return xmlSignatureFactory.newSignedInfo(canonicalizationMethod, signatureMethod, Collections.singletonList(reference));
        } catch (NoSuchAlgorithmException | InvalidAlgorithmParameterException e) {
            throw new RuntimeException(e);
        }
    }

    private DocumentBuilder initBuilder(DocumentBuilderFactory dbf) {
        try {
            return dbf.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
    }
}
