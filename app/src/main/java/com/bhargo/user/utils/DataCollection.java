package com.bhargo.user.utils;

import android.util.Log;

import com.bhargo.user.Java_Beans.API_InputParam_Bean;
import com.bhargo.user.Java_Beans.Data;
import com.bhargo.user.Java_Beans.DataManagementOptions;
import com.bhargo.user.Java_Beans.DetailedPageUISettings;
import com.bhargo.user.Java_Beans.MainContainerUISettings;
import com.bhargo.user.Java_Beans.OrderBy;
import com.bhargo.user.Java_Beans.SubContainerBodySettings;
import com.bhargo.user.Java_Beans.SubContainerHeaderSettings;
import com.bhargo.user.Java_Beans.SubContainerSettings;
import com.bhargo.user.Java_Beans.VisibilityManagementOptions;
import com.bhargo.user.pojos.FormLevelTranslation;
import com.bhargo.user.pojos.Control;
import com.bhargo.user.pojos.EditOrViewColumns;
import com.bhargo.user.pojos.FormControls;
import com.bhargo.user.pojos.SubControl;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class DataCollection {


    String TAG = "DataCollection";


    public static FormControls getControlList(String xmlDesignFormat) {
        FormControls formControls = new FormControls();
        try {
            if (xmlDesignFormat.startsWith("N")) {
                xmlDesignFormat = xmlDesignFormat.substring(1);
            }
            xmlDesignFormat = xmlDesignFormat.replaceAll("&(?!amp;)", "&amp;");
            xmlDesignFormat = xmlDesignFormat.replaceAll("CNAME", "CDATA");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(xmlDesignFormat)));
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getChildNodes().item(0).getChildNodes();
            for (int i = 0; i < nList.getLength(); i++) {
                Node nNode = nList.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    if (nNode.getNodeName().trim().equalsIgnoreCase("FormControls")) {
                        List<Control> mainFormControlsList = new ArrayList<>();
                        List<SubControl> subFormControlsList = new ArrayList<>();
                        NodeList ControleNodeList = nNode.getChildNodes();
                        for (int j = 0; j < ControleNodeList.getLength(); j++) {
                            Node ControlNode = ControleNodeList.item(j);
                            if (ControlNode.getNodeType() == Node.ELEMENT_NODE) {
                                if (!ControlNode.getNodeName().contentEquals("RTL")) {
                                    if (ControlNode.getNodeName().trim().equalsIgnoreCase(AppConstants.CONTROL_TYPE_SUBFORM) || ControlNode.getNodeName().trim().equalsIgnoreCase(AppConstants.CONTROL_TYPE_GRID_CONTROL)) {
                                        SubControl subControl = new SubControl();
                                        subControl.setSubFormName(((Element) ControlNode).getAttribute("controlName").trim());
                                        Node node;
                                        node = ((Element) ControlNode).getElementsByTagName("SubFormControls").item(0);
                                        if (node == null) {
                                            node = ((Element) ControlNode).getElementsByTagName("GridFormControls").item(0);
                                        }
                                        NodeList SubControleNodeList = node.getChildNodes();
                                        List<Control> subFormControls = new ArrayList<>();
                                        for (int k = 0; k < SubControleNodeList.getLength(); k++) {
                                            Node SubControlNode = SubControleNodeList.item(k);
                                            if (SubControlNode.getNodeType() == Node.ELEMENT_NODE) {
                                                Control control = new Control();
                                                control.setControlName(((Element) SubControlNode).getAttribute("controlName").trim());
                                                control.setControlType(SubControlNode.getNodeName().trim());
                                                subFormControls.add(control);
                                            }
                                        }
                                        subControl.setSubformControlsList(subFormControls);
                                        subFormControlsList.add(subControl);
                                    }else  if (ControlNode.getNodeName().trim().equalsIgnoreCase(AppConstants.CONTROL_TYPE_SECTION)) {
                                        Node sectionNode = ControlNode.getChildNodes().item(2);
                                        NodeList sectionControleNodeList = sectionNode.getChildNodes();
                                        for (int js = 0; js < sectionControleNodeList.getLength(); js++) {
                                            Node sectionControlNode = sectionControleNodeList.item(js);
                                            if (sectionControlNode.getNodeType() == Node.ELEMENT_NODE) {
                                                if (!sectionControlNode.getNodeName().contentEquals("RTL")) {
                                                    if (sectionControlNode.getNodeName().trim().equalsIgnoreCase(AppConstants.CONTROL_TYPE_SUBFORM) || sectionControlNode.getNodeName().trim().equalsIgnoreCase(AppConstants.CONTROL_TYPE_GRID_CONTROL)) {


                                                        SubControl subControl = new SubControl();
                                                        subControl.setSubFormName(((Element) sectionControlNode).getAttribute("controlName").trim());
                                                        Node node;
                                                        node = ((Element) sectionControlNode).getElementsByTagName("SubFormControls").item(0);
                                                        if (node == null) {
                                                            node = ((Element) sectionControlNode).getElementsByTagName("GridFormControls").item(0);
                                                        }
                                                        NodeList SubControleNodeList = node.getChildNodes();
                                                        List<Control> subFormControls = new ArrayList<>();
                                                        for (int k = 0; k < SubControleNodeList.getLength(); k++) {
                                                            Node SubControlNode = SubControleNodeList.item(k);
                                                            if (SubControlNode.getNodeType() == Node.ELEMENT_NODE) {
                                                                Control control = new Control();
                                                                control.setControlName(((Element) SubControlNode).getAttribute("controlName").trim());
                                                                control.setControlType(SubControlNode.getNodeName().trim());
                                                                subFormControls.add(control);
                                                            }
                                                        }
                                                        subControl.setSubformControlsList(subFormControls);
                                                        subFormControlsList.add(subControl);
                                                    }}}}} else {
                                        Control control = new Control();
                                        control.setControlName(((Element) ControlNode).getAttribute("controlName").trim());
                                        control.setControlType(ControlNode.getNodeName().trim());
                                        if (ControlNode.getNodeName().trim().equalsIgnoreCase(AppConstants.CONTROL_TYPE_CAMERA)) {
                                            if (((Element) ControlNode).getElementsByTagName("Options").getLength() != 0) {
                                                NodeList cameraOptionsList = ((Element) ControlNode).getElementsByTagName("Options").item(0).getChildNodes();
                                                for (int k = 0; k < cameraOptionsList.getLength(); k++) {
                                                    Node Opton = cameraOptionsList.item(k);
                                                    if (Opton.getNodeType() == Node.ELEMENT_NODE) {
                                                        if (Opton.getNodeName().trim().equalsIgnoreCase("ImageGPS")) {
                                                            control.setEnableImageWithGps(Boolean.parseBoolean(Opton.getTextContent().trim()));
                                                            break;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        mainFormControlsList.add(control);
                                    }
                                }
                            }
                        }
                        formControls.setMainFormControlsList(mainFormControlsList);
                        formControls.setSubFormControlsList(subFormControlsList);
                    }
                }
            }
        } catch (Exception e) {
            Log.getStackTraceString(e);
        }

        return formControls;


    }

    public static DataManagementOptions getAdvanceManagement(String xmlDesignFormat) {
        DataManagementOptions dataManagementOptions = new DataManagementOptions();
        try {
            if (xmlDesignFormat.startsWith("N")) {
                xmlDesignFormat = xmlDesignFormat.substring(1);
            }
            xmlDesignFormat = xmlDesignFormat.replaceAll("&(?!amp;)", "&amp;");
            xmlDesignFormat = xmlDesignFormat.replaceAll("CNAME", "CDATA");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(xmlDesignFormat)));
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getChildNodes().item(0).getChildNodes();
            for (int i = 0; i < nList.getLength(); i++) {
                Node nNode = nList.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    if (nNode.getNodeName().trim().equalsIgnoreCase("TableSettings")) {
                        NodeList ControlNodeList = nNode.getChildNodes();
                        if (ControlNodeList.getLength() > 0) {
                            for (int j = 0; j < ControlNodeList.getLength(); j++) {
                                Node ControlNode = ControlNodeList.item(j);
                                if (ControlNode.getNodeName().equalsIgnoreCase("MainTableSettings")) {
                                    setTableSettingsObject(dataManagementOptions, ControlNode);
                                }
                            }
                        }
                    } else if (nNode.getNodeName().trim().equalsIgnoreCase("AdvanceManagement")) {
                        break;
                    }
                }

            }
        } catch (Exception e) {
            System.out.println("Exception:AdvanceManagement in" + ":" + e.getMessage());
            dataManagementOptions = null;
        }
        return dataManagementOptions;
    }

/*    public static DataManagementOptions getListTableColumns (String xmlDesignFormat){
        DataManagementOptions dataManagementOptions = new DataManagementOptions();
        try {
            if (xmlDesignFormat.startsWith("N")) {
                xmlDesignFormat = xmlDesignFormat.substring(1);
            }
            xmlDesignFormat = xmlDesignFormat.replaceAll("&(?!amp;)", "&amp;");
            xmlDesignFormat = xmlDesignFormat.replaceAll("CNAME", "CDATA");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(xmlDesignFormat)));
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getChildNodes().item(0).getChildNodes();
            for (int i = 0; i < nList.getLength(); i++) {
                Node nNode = nList.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    if (nNode.getNodeName().trim().equalsIgnoreCase("ViewColumns")) {
                        NodeList columnsChildList = nNode.getChildNodes();
                        List<String> TableColumnNames = new ArrayList<>();
                        for (int childCnt = 0; childCnt < columnsChildList.getLength(); childCnt++) {
                            Element settingFieldsElement = (Element) columnsChildList.item(childCnt);
                            if (settingFieldsElement.getNodeName().equals("ColumnName")) {
                                String TableColumnName = getCharacterDataFromElement(settingFieldsElement);
                                TableColumnNames.add(TableColumnName);
                            }
                        }
                        dataManagementOptions.setPreviewColumns(TableColumnNames);
                    } else if (nNode.getNodeName().trim().equalsIgnoreCase("EditColumns")) {
                        NodeList columnsChildList = nNode.getChildNodes();
                        List<EditOrViewColumns> editOrViewColumnsList = new ArrayList<>();
                        for (int childCnt = 0; childCnt < columnsChildList.getLength(); childCnt++) {
                            EditOrViewColumns editOrViewColumns = new EditOrViewColumns();
                            Element settingFieldsElement = (Element) columnsChildList.item(childCnt);
                            if (settingFieldsElement.getNodeName().equals("ColumnName")) {
                                String TableColumnName = getCharacterDataFromElement(settingFieldsElement);
                                editOrViewColumns.setColumnName(TableColumnName);
                            }
                            editOrViewColumns.setColumnType(settingFieldsElement.getAttribute("type"));
                            editOrViewColumns.setFormName(settingFieldsElement.getAttribute("subformName"));
                            editOrViewColumnsList.add(editOrViewColumns);
                        }
                        dataManagementOptions.setEditColumns(editOrViewColumnsList);

                    } else if (nNode.getNodeName().trim().equalsIgnoreCase("TableSettings")) {
                        NodeList ControlNodeList = nNode.getChildNodes();
                        if (ControlNodeList.getLength() > 0) {
                            for (int j = 0; j < ControlNodeList.getLength(); j++) {
                                Node ControlNode = ControlNodeList.item(j);
                                if (ControlNode.getNodeName().equalsIgnoreCase("MainTableSettings")) {
                                    setTableSettingsObject(dataManagementOptions,  ControlNode);
                                } *//*else if (ControlNode.getNodeName().equalsIgnoreCase("SubFormTableSettings")) {
                                        for (ControlObject controlObject : AppObject.getControls_list()) {
                                            String subFormName = ((Element) ControlNode).getAttribute("name").trim();
                                            if (controlObject.getControlName().equalsIgnoreCase(subFormName))
                                                setTableSettingsObject(null, controlObject, ControlNode, 1,filterSubFormColumnsList);
                                        }

                                    }*//*

                            }
                        }
//                        setTableSettingsObject(dataManagementOptions,  nNode);
                    } else if (nNode.getNodeName().trim().equalsIgnoreCase("AdvanceManagement")) {
                        NodeList advanceManagementChildList = nNode.getChildNodes();

                        for (int advanceManagementCnt = 0; advanceManagementCnt < advanceManagementChildList.getLength(); advanceManagementCnt++) {
                            Element advanceManagementElement = (Element) advanceManagementChildList.item(advanceManagementCnt);

                            if (advanceManagementElement.getNodeName().equals("EnableViewData")) {
                                String enableViewData = XMLHelper.getCharacterDataFromElement(advanceManagementElement);

                                dataManagementOptions.setEnableViewData(Boolean.parseBoolean(enableViewData));
                            }
                            if (advanceManagementElement.getNodeName().equalsIgnoreCase("OrderByColumnsIndexPage")) {
                                OrderBy orderBy = new OrderBy();
                                String orderByColumns = XMLHelper.getCharacterDataFromElement(advanceManagementElement);
                                dataManagementOptions.setIndexPageColumnsOrderList(orderByColumns);
                            }
                            if (advanceManagementElement.getNodeName().equalsIgnoreCase("Order")) {
                                String order = XMLHelper.getCharacterDataFromElement(advanceManagementElement);
                                if (order != null && dataManagementOptions.getIndexPageColumnsOrderList() != null) {
                                    dataManagementOptions.setIndexPageColumnsOrder(order);
                                }
                            }

                            if (advanceManagementElement.getNodeName().equalsIgnoreCase("OrderByColumnsViewPage")) {
                                String orderByColumns = XMLHelper.getCharacterDataFromElement(advanceManagementElement);
                                if (orderByColumns != null && !orderByColumns.contentEquals("")) {
                                    List<String> orderByViewPage = new ArrayList<>();
                                    for (int j = 0; j < orderByColumns.split(",").length; j++) {
                                        orderByViewPage.add(orderByColumns.split(",")[j]);
                                    }
                                    dataManagementOptions.setViewPageColumnsOrder(orderByViewPage);
                                }
                            }
                            if (advanceManagementElement.getNodeName().equals("EnableEditData")) {
                                String enableEditData = XMLHelper.getCharacterDataFromElement(advanceManagementElement);

                                dataManagementOptions.setEnableEditData(Boolean.parseBoolean(enableEditData));
                            }

                            if (advanceManagementElement.getNodeName().equals("EnableDeleteData")) {
                                String enableDeleteData = XMLHelper.getCharacterDataFromElement(advanceManagementElement);
                                dataManagementOptions.setEnableDeleteData(Boolean.parseBoolean(enableDeleteData));
                            }
                            if (advanceManagementElement.getNodeName().equals("AddNewRecord")) {
                                String addNewRecord = XMLHelper.getCharacterDataFromElement(advanceManagementElement);
                                dataManagementOptions.setAddNewRecord(Boolean.parseBoolean(addNewRecord));
                            }

                            if (advanceManagementElement.getNodeName().equals("AllowOnlyOneRecord")) {
                                String allowOnlyOneRecord = XMLHelper.getCharacterDataFromElement(advanceManagementElement);
                                dataManagementOptions.setAllowOnlyOneRecord(Boolean.parseBoolean(allowOnlyOneRecord));
                            }

                            if (advanceManagementElement.getNodeName().equals("LazyLoading")) {
                                String lazyLoading = XMLHelper.getCharacterDataFromElement(advanceManagementElement);
                                dataManagementOptions.setLazyLoadingEnabled(Boolean.parseBoolean(lazyLoading));
                            }
                            if (dataManagementOptions.isLazyLoadingEnabled()) {
                                if (advanceManagementElement.getNodeName().equals("LazyLoadingThreshold")) {
                                    String lazyLoadingThreshold = XMLHelper.getCharacterDataFromElement(advanceManagementElement);
                                    dataManagementOptions.setLazyLoadingThreshold(lazyLoadingThreshold);
                                }
                            }

                            if (advanceManagementElement.getNodeName().equals("CaptionForAdd")) {
                                String captionForAdd = XMLHelper.getCharacterDataFromElement(advanceManagementElement);
                                dataManagementOptions.setCaptionForAdd(captionForAdd);

                            }

                            if (advanceManagementElement.getNodeName().equals("FetchData")) {
                                String fetchData = XMLHelper.getCharacterDataFromElement(advanceManagementElement);
                                dataManagementOptions.setFetchData(fetchData);
                            }

                        }
                        break;
                    }}

            }
        } catch (Exception e) {
            System.out.println("Exception:AdvanceManagement in" +  ":" + e.getMessage());
            dataManagementOptions = null;
        }
        return dataManagementOptions;
    }*/

    public static DataManagementOptions getDataPermissions(String xmlDesignFormat) {
        DataManagementOptions dataManagementOptions = new DataManagementOptions();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(xmlDesignFormat)));
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getChildNodes().item(0).getChildNodes();
            for (int i = 0; i < nList.getLength(); i++) {
                Node nNode = nList.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    if (nNode.getNodeName().trim().equalsIgnoreCase("AdvanceManagement")) {
                        dataManagementOptions = getAdvancedSettings(nNode);
                        /*NodeList advanceManagementChildList = nNode.getChildNodes();
                        for (int advanceManagementCnt = 0; advanceManagementCnt < advanceManagementChildList.getLength(); advanceManagementCnt++) {
                            Element advanceManagementElement = (Element) advanceManagementChildList.item(advanceManagementCnt);
                            if (advanceManagementElement.getNodeName().equals("MobileSettings")) {
                                NodeList mobileSettingsChildList = advanceManagementElement.getChildNodes();
                        for (int mobileSettingsCnt = 0; mobileSettingsCnt < mobileSettingsChildList.getLength(); mobileSettingsCnt++) {
                            Element mobileSettingsElement = (Element) mobileSettingsChildList.item(mobileSettingsCnt);
                            if (mobileSettingsElement.getNodeName().equals("IndexPageDetails")) {
                                dataManagementOptions.getIndexPageDetails().setIndexPage(Boolean.parseBoolean(mobileSettingsElement.getAttribute("isIndexPage")));
                                NodeList IndexPageDetailsChildList = mobileSettingsElement.getChildNodes();
                                for (int IndexPageDetailsCnt = 0; IndexPageDetailsCnt < IndexPageDetailsChildList.getLength(); IndexPageDetailsCnt++) {
                                    Element IndexPageDetailsElement = (Element) IndexPageDetailsChildList.item(IndexPageDetailsCnt);
                                    if (IndexPageDetailsElement.getNodeName().equals("IndexPageTemplateId")) {
                                        String indexPageTemplateId = XMLHelper.getCharacterDataFromElement(IndexPageDetailsElement);
                                        dataManagementOptions.getIndexPageDetails().setIndexPageTemplateId(Integer.parseInt(indexPageTemplateId));
                                    }
                                    if (IndexPageDetailsElement.getNodeName().equals("Data")) {
                                        NodeList dataChildList = IndexPageDetailsElement.getChildNodes();
                                        for (int dataChildListCnt = 0; dataChildListCnt < dataChildList.getLength(); dataChildListCnt++) {
                                            Element dataElement = (Element) dataChildList.item(dataChildListCnt);
                                            if (dataElement.getNodeName().equals("PageTitle")) {
                                                String pageTitle = XMLHelper.getCharacterDataFromElement(dataElement);
                                                dataManagementOptions.getIndexPageDetails().setPageTitle(pageTitle);
                                            } else if (dataElement.getNodeName().equals("Image")) {
                                                String image = XMLHelper.getCharacterDataFromElement(dataElement);
                                                dataManagementOptions.getIndexPageDetails().setImage(image);
                                            } else if (dataElement.getNodeName().equals("ProfileImage")) {
                                                String profileImage = XMLHelper.getCharacterDataFromElement(dataElement);
                                                dataManagementOptions.getIndexPageDetails().setProfileImage(profileImage);
                                            } else if (dataElement.getNodeName().equals("Header")) {
                                                String header = XMLHelper.getCharacterDataFromElement(dataElement);
                                                dataManagementOptions.getIndexPageDetails().setHeader(header);
                                            } else if (dataElement.getNodeName().equals("SubHeader")) {
                                                String subHeader = XMLHelper.getCharacterDataFromElement(dataElement);
                                                dataManagementOptions.getIndexPageDetails().setSubHeader(subHeader);
                                            } else if (dataElement.getNodeName().equalsIgnoreCase("Description")) {
                                                List<String> descriptionItems = new ArrayList<>();
                                                NodeList columnsChildList = dataElement.getChildNodes();
                                                for (int childCnt = 0; childCnt < columnsChildList.getLength(); childCnt++) {
                                                    Element descriptionElement = (Element) columnsChildList.item(childCnt);
                                                    if (descriptionElement.getNodeName().equals("DescriptionItem")) {
                                                        String columnName = getCharacterDataFromElement(descriptionElement);
                                                        descriptionItems.add(columnName);
                                                    }
                                                }
                                                dataManagementOptions.getIndexPageDetails().setDescriptionList(descriptionItems);
                                            }
                                        }
                                    } else if (IndexPageDetailsElement.getNodeName().equals("UISettings")) {
                                        NodeList UISettingsChildList = IndexPageDetailsElement.getChildNodes();
                                        for (int UISettingsCnt = 0; UISettingsCnt < UISettingsChildList.getLength(); UISettingsCnt++) {
                                            Element UISettingsElement = (Element) UISettingsChildList.item(UISettingsCnt);
                                            if (UISettingsElement.getNodeName().equals("HeaderFontSize")) {
                                                String headerFontSize = XMLHelper.getCharacterDataFromElement(UISettingsElement);
                                                dataManagementOptions.getIndexPageDetails().getIndexPageUISettings().setHeaderFontSize(headerFontSize);
                                            } else if (UISettingsElement.getNodeName().equals("HeaderFontStyle")) {
                                                String headerFontStyle = XMLHelper.getCharacterDataFromElement(UISettingsElement);
                                                dataManagementOptions.getIndexPageDetails().getIndexPageUISettings().setHeaderFontStyle(headerFontStyle);
                                            } else if (UISettingsElement.getNodeName().equals("HeaderFontColor")) {
                                                String headerFontColor = XMLHelper.getCharacterDataFromElement(UISettingsElement);
                                                dataManagementOptions.getIndexPageDetails().getIndexPageUISettings().setHeaderFontColor(headerFontColor);
                                            } else if (UISettingsElement.getNodeName().equals("SubHeaderFontSize")) {
                                                String subHeaderFontSize = XMLHelper.getCharacterDataFromElement(UISettingsElement);
                                                dataManagementOptions.getIndexPageDetails().getIndexPageUISettings().setSubHeaderFontSize(subHeaderFontSize);
                                            } else if (UISettingsElement.getNodeName().equals("SubHeaderFontStyle")) {
                                                String subHeaderFontStyle = XMLHelper.getCharacterDataFromElement(UISettingsElement);
                                                dataManagementOptions.getIndexPageDetails().getIndexPageUISettings().setSubHeaderFontStyle(subHeaderFontStyle);
                                            } else if (UISettingsElement.getNodeName().equals("SubHeaderFontColor")) {
                                                String subHeaderFontColor = XMLHelper.getCharacterDataFromElement(UISettingsElement);
                                                dataManagementOptions.getIndexPageDetails().getIndexPageUISettings().setSubHeaderFontColor(subHeaderFontColor);
                                            } else if (UISettingsElement.getNodeName().equals("DescriptionFontSize")) {
                                                String descriptionFontSize = XMLHelper.getCharacterDataFromElement(UISettingsElement);
                                                dataManagementOptions.getIndexPageDetails().getIndexPageUISettings().setDescriptionFontSize(descriptionFontSize);
                                            } else if (UISettingsElement.getNodeName().equals("DescriptionFontStyle")) {
                                                String descriptionFontStyle = XMLHelper.getCharacterDataFromElement(UISettingsElement);
                                                dataManagementOptions.getIndexPageDetails().getIndexPageUISettings().setDescriptionFontStyle(descriptionFontStyle);
                                            } else if (UISettingsElement.getNodeName().equals("DescriptionFontColor")) {
                                                String descriptionFontColor = XMLHelper.getCharacterDataFromElement(UISettingsElement);
                                                dataManagementOptions.getIndexPageDetails().getIndexPageUISettings().setDescriptionFontColor(descriptionFontColor);
                                            }
                                        }
                                    }
                                }
                            } else if (mobileSettingsElement.getNodeName().equals("DetailedPageDetails")) {
                                dataManagementOptions.getDetailedPageDetails().setDetailPage(Boolean.parseBoolean(mobileSettingsElement.getAttribute("isDetailPage")));
                                NodeList detailedPageDetailsChildList = mobileSettingsElement.getChildNodes();
                                for (int detailedPageDetailsCnt = 0; detailedPageDetailsCnt < detailedPageDetailsChildList.getLength(); detailedPageDetailsCnt++) {
                                    Element detailedPageDetailsElement = (Element) detailedPageDetailsChildList.item(detailedPageDetailsCnt);
                                    if (detailedPageDetailsElement.getNodeName().equals("DetailedPageTemplateId")) {
                                        String detailedPageTemplateId = XMLHelper.getCharacterDataFromElement(detailedPageDetailsElement);
                                        dataManagementOptions.getDetailedPageDetails().setDetailedPageTemplateId(Integer.parseInt(detailedPageTemplateId));
                                    } else if (detailedPageDetailsElement.getNodeName().equals("HeaderSettings")) {
                                        dataManagementOptions.getDetailedPageDetails().getDetailedPageHeaderSettings().setHeaderLayout(Boolean.parseBoolean(detailedPageDetailsElement.getAttribute("isHeaderLayout")));
                                        NodeList HeaderSettingsChildList = detailedPageDetailsElement.getChildNodes();
                                        for (int HeaderSettingsChildListCnt = 0; HeaderSettingsChildListCnt < HeaderSettingsChildList.getLength(); HeaderSettingsChildListCnt++) {
                                            Element headerSettingElement = (Element) HeaderSettingsChildList.item(HeaderSettingsChildListCnt);
                                            if (headerSettingElement.getNodeName().equals("Data")) {
                                                NodeList dataChildList = headerSettingElement.getChildNodes();
                                                Data data = new Data();
                                                for (int dataChildListCnt = 0; dataChildListCnt < dataChildList.getLength(); dataChildListCnt++) {
                                                    Element dataElement = (Element) dataChildList.item(dataChildListCnt);
                                                    if (dataElement.getNodeName().equals("PageTitle")) {
                                                        String pageTitle = XMLHelper.getCharacterDataFromElement(dataElement);
                                                        data.setPageTitle(pageTitle);
                                                    } else if (dataElement.getNodeName().equals("Image")) {
                                                        String image = XMLHelper.getCharacterDataFromElement(dataElement);
                                                        data.setImage(image);
                                                    } else if (dataElement.getNodeName().equals("Header")) {
                                                        String header = XMLHelper.getCharacterDataFromElement(dataElement);
                                                        data.setHeader(header);
                                                    } else if (dataElement.getNodeName().equals("SubHeader")) {
                                                        String subHeader = XMLHelper.getCharacterDataFromElement(dataElement);
                                                        data.setSubHeader(subHeader);
                                                    } else if (dataElement.getNodeName().equalsIgnoreCase("Description")) {
                                                        List<String> descriptionItems = new ArrayList<>();
                                                        NodeList columnsChildList = dataElement.getChildNodes();
                                                        for (int childCnt = 0; childCnt < columnsChildList.getLength(); childCnt++) {
                                                            Element descriptionElement = (Element) columnsChildList.item(childCnt);
                                                            if (descriptionElement.getNodeName().equals("DescriptionItem")) {
                                                                String columnName = getCharacterDataFromElement(descriptionElement);
                                                                descriptionItems.add(columnName);
                                                            }
                                                        }
                                                        data.setDescriptionList(descriptionItems);
                                                    } else if (dataElement.getNodeName().equals("CardOneImage")) {
                                                        String cardOneImage = XMLHelper.getCharacterDataFromElement(dataElement);
                                                        data.setCardOneImage(cardOneImage);
                                                    } else if (dataElement.getNodeName().equals("CardOneLable")) {
                                                        String cardOneLable = XMLHelper.getCharacterDataFromElement(dataElement);
                                                        data.setCardOneLable(cardOneLable);
                                                    } else if (dataElement.getNodeName().equals("CardOneValue")) {
                                                        String cardOneValue = XMLHelper.getCharacterDataFromElement(dataElement);
                                                        data.setCardOneValue(cardOneValue);
                                                    } else if (dataElement.getNodeName().equals("CardTwoImage")) {
                                                        String cardTwoImage = XMLHelper.getCharacterDataFromElement(dataElement);
                                                        data.setCardTwoImage(cardTwoImage);
                                                    } else if (dataElement.getNodeName().equals("CardTwoLable")) {
                                                        String cardTwoLable = XMLHelper.getCharacterDataFromElement(dataElement);
                                                        data.setCardTwoLable(cardTwoLable);
                                                    } else if (dataElement.getNodeName().equals("CardTwoValue")) {
                                                        String cardTwoValue = XMLHelper.getCharacterDataFromElement(dataElement);
                                                        data.setCardTwoValue(cardTwoValue);
                                                    }else if (dataElement.getNodeName().equals("ProfileImage")) {
                                                        String profileImage = XMLHelper.getCharacterDataFromElement(dataElement);
                                                        data.setProfileImage(profileImage);
                                                    }else if (dataElement.getNodeName().equals("Date")) {
                                                        String date = XMLHelper.getCharacterDataFromElement(dataElement);
                                                        data.setDate(date);
                                                    }else if (dataElement.getNodeName().equals("Title")) {
                                                        String title = XMLHelper.getCharacterDataFromElement(dataElement);
                                                        data.setTitle(title);
                                                    }
                                                }
                                                dataManagementOptions.getDetailedPageDetails().getDetailedPageHeaderSettings().setData(data);
                                            } else if (headerSettingElement.getNodeName().equals("UISettings")) {
                                                NodeList UISettingsChildList = headerSettingElement.getChildNodes();
                                                DetailedPageUISettings detailedPageUISettings = new DetailedPageUISettings();
                                                for (int UISettingsCnt = 0; UISettingsCnt < UISettingsChildList.getLength(); UISettingsCnt++) {
                                                    Element UISettingsElement = (Element) UISettingsChildList.item(UISettingsCnt);
                                                    if (UISettingsElement.getNodeName().equals("HeaderFontSize")) {
                                                        String headerFontSize = XMLHelper.getCharacterDataFromElement(UISettingsElement);
                                                        detailedPageUISettings.setHeaderFontSize(headerFontSize);
                                                    } else if (UISettingsElement.getNodeName().equals("HeaderFontStyle")) {
                                                        String headerFontStyle = XMLHelper.getCharacterDataFromElement(UISettingsElement);
                                                        detailedPageUISettings.setHeaderFontStyle(headerFontStyle);
                                                    } else if (UISettingsElement.getNodeName().equals("HeaderFontColor")) {
                                                        String headerFontColor = XMLHelper.getCharacterDataFromElement(UISettingsElement);
                                                        detailedPageUISettings.setHeaderFontColor(headerFontColor);
                                                    } else if (UISettingsElement.getNodeName().equals("SubHeaderFontSize")) {
                                                        String subHeaderFontSize = XMLHelper.getCharacterDataFromElement(UISettingsElement);
                                                        detailedPageUISettings.setSubHeaderFontSize(subHeaderFontSize);
                                                    } else if (UISettingsElement.getNodeName().equals("SubHeaderFontStyle")) {
                                                        String subHeaderFontStyle = XMLHelper.getCharacterDataFromElement(UISettingsElement);
                                                        detailedPageUISettings.setSubHeaderFontStyle(subHeaderFontStyle);
                                                    } else if (UISettingsElement.getNodeName().equals("SubHeaderFontColor")) {
                                                        String subHeaderFontColor = XMLHelper.getCharacterDataFromElement(UISettingsElement);
                                                        detailedPageUISettings.setSubHeaderFontColor(subHeaderFontColor);
                                                    } else if (UISettingsElement.getNodeName().equals("DescriptionFontSize")) {
                                                        String descriptionFontSize = XMLHelper.getCharacterDataFromElement(UISettingsElement);
                                                        detailedPageUISettings.setDescriptionFontSize(descriptionFontSize);
                                                    } else if (UISettingsElement.getNodeName().equals("DescriptionFontStyle")) {
                                                        String descriptionFontStyle = XMLHelper.getCharacterDataFromElement(UISettingsElement);
                                                        detailedPageUISettings.setDescriptionFontStyle(descriptionFontStyle);
                                                    } else if (UISettingsElement.getNodeName().equals("DescriptionFontColor")) {
                                                        String descriptionFontColor = XMLHelper.getCharacterDataFromElement(UISettingsElement);
                                                        detailedPageUISettings.setDescriptionFontColor(descriptionFontColor);
                                                    } else if (UISettingsElement.getNodeName().equals("BackgroundColor")) {
                                                        String backgroundColor = XMLHelper.getCharacterDataFromElement(UISettingsElement);
                                                        detailedPageUISettings.setBackgroundColor(backgroundColor);
                                                    } else if (UISettingsElement.getNodeName().equals("CardOneLableFontColor")) {
                                                        String cardOneLableFontColor = XMLHelper.getCharacterDataFromElement(UISettingsElement);
                                                        detailedPageUISettings.setCardOneLabelFontColor(cardOneLableFontColor);
                                                    } else if (UISettingsElement.getNodeName().equals("CardOneLableFontSize")) {
                                                        String cardOneLableFontSize = XMLHelper.getCharacterDataFromElement(UISettingsElement);
                                                        detailedPageUISettings.setCardOneLabelFontSize(cardOneLableFontSize);
                                                    } else if (UISettingsElement.getNodeName().equals("CardOneValueFontColor")) {
                                                        String cardOneValueFontColor = XMLHelper.getCharacterDataFromElement(UISettingsElement);
                                                        detailedPageUISettings.setCardOneValueFontColor(cardOneValueFontColor);
                                                    } else if (UISettingsElement.getNodeName().equals("CardOneValueFontSize")) {
                                                        String cardOneValueFontSize = XMLHelper.getCharacterDataFromElement(UISettingsElement);
                                                        detailedPageUISettings.setCardOneValueFontSize(cardOneValueFontSize);
                                                    } else if (UISettingsElement.getNodeName().equals("CardOneBackgroundColor")) {
                                                        String cardOneBackgroundColor = XMLHelper.getCharacterDataFromElement(UISettingsElement);
                                                        detailedPageUISettings.setCardOneBackgroundColor(cardOneBackgroundColor);
                                                    } else if (UISettingsElement.getNodeName().equals("CardTwoLableFontColor")) {
                                                        String cardTwoLableFontColor = XMLHelper.getCharacterDataFromElement(UISettingsElement);
                                                        detailedPageUISettings.setCardTwoLabelFontColor(cardTwoLableFontColor);
                                                    } else if (UISettingsElement.getNodeName().equals("CardTwoLableFontSize")) {
                                                        String cardTwoLableFontSize = XMLHelper.getCharacterDataFromElement(UISettingsElement);
                                                        detailedPageUISettings.setCardTwoLabelFontSize(cardTwoLableFontSize);
                                                    } else if (UISettingsElement.getNodeName().equals("CardTwoValueFontColor")) {
                                                        String cardTwoValueFontColor = XMLHelper.getCharacterDataFromElement(UISettingsElement);
                                                        detailedPageUISettings.setCardTwoValueFontColor(cardTwoValueFontColor);
                                                    } else if (UISettingsElement.getNodeName().equals("CardTwoValueFontSize")) {
                                                        String cardTwoValueFontSize = XMLHelper.getCharacterDataFromElement(UISettingsElement);
                                                        detailedPageUISettings.setCardTwoValueFontSize(cardTwoValueFontSize);
                                                    } else if (UISettingsElement.getNodeName().equals("CardTwoBackgroundColor")) {
                                                        String cardTwoBackgroundColor = XMLHelper.getCharacterDataFromElement(UISettingsElement);
                                                        detailedPageUISettings.setCardTwoBackgroundColor(cardTwoBackgroundColor);
                                                    }
                                                }
                                                dataManagementOptions.getDetailedPageDetails().getDetailedPageHeaderSettings().setDetailedPageUISettings(detailedPageUISettings);
                                            }
                                        }
                                    } else if (detailedPageDetailsElement.getNodeName().equals("BodySettings")) {
                                        NodeList bodySettingsChildList = detailedPageDetailsElement.getChildNodes();
                                        for (int bodySettingsChildListCnt = 0; bodySettingsChildListCnt < bodySettingsChildList.getLength(); bodySettingsChildListCnt++) {
                                            Element bodySettingsElement = (Element) bodySettingsChildList.item(bodySettingsChildListCnt);
                                            if (bodySettingsElement.getNodeName().equals("LeftLayoutSettings")) {
                                                if(!dataManagementOptions.getDetailedPageDetails().getDetailedPageHeaderSettings().isHeaderLayout()){
                                                    dataManagementOptions.getDetailedPageDetails().getBodySettings().getLeftLayoutSettings().setDefaultPageTitle(bodySettingsElement.getAttribute("defaultPageTitle"));
                                                }
                                                dataManagementOptions.getDetailedPageDetails().getBodySettings().getLeftLayoutSettings().setLeftLayout(Boolean.parseBoolean(bodySettingsElement.getAttribute("isLeftLayout")));
                                                NodeList LeftLayoutSettingsChildList = bodySettingsElement.getChildNodes();
                                                for (int LeftLayoutSettingsChildListCnt = 0; LeftLayoutSettingsChildListCnt < LeftLayoutSettingsChildList.getLength(); LeftLayoutSettingsChildListCnt++) {
                                                    Element LeftLayoutSettingsElement = (Element) LeftLayoutSettingsChildList.item(LeftLayoutSettingsChildListCnt);
                                                    if (LeftLayoutSettingsElement.getNodeName().equals("MainContainerSettings")) {
                                                        NodeList MainContainerSettingsChildList = LeftLayoutSettingsElement.getChildNodes();
                                                        for (int MainContainerSettingsChildListCnt = 0; MainContainerSettingsChildListCnt < MainContainerSettingsChildList.getLength(); MainContainerSettingsChildListCnt++) {
                                                            Element MainContainerSettingsChildElement = (Element) MainContainerSettingsChildList.item(MainContainerSettingsChildListCnt);
                                                            if (MainContainerSettingsChildElement.getNodeName().equals("MainContainerTemplateId")) {
                                                                String mainContainerTemplateId = XMLHelper.getCharacterDataFromElement(MainContainerSettingsChildElement);
                                                                    dataManagementOptions.getDetailedPageDetails().getBodySettings().getLeftLayoutSettings().getMainContainerSettings().setMainContainerTemplateId(Integer.parseInt(mainContainerTemplateId));
                                                            } else if (MainContainerSettingsChildElement.getNodeName().equals("Data")) {
                                                                NodeList dataChildList = MainContainerSettingsChildElement.getChildNodes();
                                                                Element dataChildElement = (Element) dataChildList.item(0);
                                                                NodeList tableColumnsChildList = dataChildElement.getChildNodes();
                                                                List<String> tableColumns = new ArrayList<>();
                                                                for (int childCnt = 0; childCnt < tableColumnsChildList.getLength(); childCnt++) {
                                                                    Element settingFieldsElement = (Element) tableColumnsChildList.item(childCnt);
                                                                    if (settingFieldsElement.getNodeName().equals("ColumnName")) {
                                                                        String TableColumnName = getCharacterDataFromElement(settingFieldsElement);
                                                                        tableColumns.add(TableColumnName);
                                                                    }
                                                                }
                                                                dataManagementOptions.getDetailedPageDetails().getBodySettings().getLeftLayoutSettings().getMainContainerSettings().setTableColumns(tableColumns);
                                                            } else if (MainContainerSettingsChildElement.getNodeName().equals("UISettings")) {
                                                                NodeList UISettingsChildList = MainContainerSettingsChildElement.getChildNodes();
                                                                MainContainerUISettings mainContainerUISettings = new MainContainerUISettings();
                                                                for (int UISettingsCnt = 0; UISettingsCnt < UISettingsChildList.getLength(); UISettingsCnt++) {
                                                                    Element UISettingsElement = (Element) UISettingsChildList.item(UISettingsCnt);
                                                                    if (UISettingsElement.getNodeName().equals("LableFontSize")) {
                                                                        String labelFontSize = XMLHelper.getCharacterDataFromElement(UISettingsElement);
                                                                        mainContainerUISettings.setLabelFontSize(Integer.parseInt(labelFontSize));
                                                                    } else if (UISettingsElement.getNodeName().equals("LableFontColor")) {
                                                                        String labelFontColor = XMLHelper.getCharacterDataFromElement(UISettingsElement);
                                                                        mainContainerUISettings.setLabelFontColor(labelFontColor);
                                                                    } else if (UISettingsElement.getNodeName().equals("ValueFontSize")) {
                                                                        String valueFontSize = XMLHelper.getCharacterDataFromElement(UISettingsElement);
                                                                        mainContainerUISettings.setValueFontSize(Integer.parseInt(valueFontSize));
                                                                    } else if (UISettingsElement.getNodeName().equals("ValueFontColor")) {
                                                                        String valueFontColor = XMLHelper.getCharacterDataFromElement(UISettingsElement);
                                                                        mainContainerUISettings.setValueFontColor(valueFontColor);
                                                                    } else if (UISettingsElement.getNodeName().equals("BorderStyle")) {
                                                                        String borderStyle = XMLHelper.getCharacterDataFromElement(UISettingsElement);
                                                                        mainContainerUISettings.setBorderStyle(borderStyle);
                                                                    } else if (UISettingsElement.getNodeName().equals("BorderColor")) {
                                                                        String borderColor = XMLHelper.getCharacterDataFromElement(UISettingsElement);
                                                                        mainContainerUISettings.setBorderColor(borderColor);
                                                                    } else if (UISettingsElement.getNodeName().equals("BackgroundColor")) {
                                                                        String backgroundColor = XMLHelper.getCharacterDataFromElement(UISettingsElement);
                                                                        mainContainerUISettings.setBackgroundColor(backgroundColor);
                                                                    } else if (UISettingsElement.getNodeName().equals("Alignment")) {
                                                                        String alignment = XMLHelper.getCharacterDataFromElement(UISettingsElement);
                                                                        mainContainerUISettings.setAlignment(alignment);
                                                                    } else if (UISettingsElement.getNodeName().equals("ActiveColor")) {
                                                                        String activeColor = XMLHelper.getCharacterDataFromElement(UISettingsElement);
                                                                        mainContainerUISettings.setActiveColor(activeColor);
                                                                    }
                                                                }
                                                                dataManagementOptions.getDetailedPageDetails().getBodySettings().getLeftLayoutSettings().getMainContainerSettings().setMainContainerUISettings(mainContainerUISettings);
                                                            }
                                                        }
                                                    }else if (LeftLayoutSettingsElement.getNodeName().equals("SubContainerSettings")) {
                                                        List<SubContainerSettings> subContainerSettingsList = new ArrayList<>();
                                                        NodeList subContainerSettingsChildList = LeftLayoutSettingsElement.getChildNodes();
                                                        for (int subContainerSettingsCnt = 0; subContainerSettingsCnt < subContainerSettingsChildList.getLength(); subContainerSettingsCnt++) {
                                                            SubContainerSettings subContainerSettings = new SubContainerSettings();
                                                            Element subContainerSettingsElement = (Element) subContainerSettingsChildList.item(subContainerSettingsCnt);
                                                        subContainerSettings.setSubControlName(subContainerSettingsElement.getAttribute("SubControlName"));
                                                        NodeList containerSettingsChildList = subContainerSettingsElement.getChildNodes();
                                                        for (int containerSettingsCnt = 0; containerSettingsCnt < containerSettingsChildList.getLength(); containerSettingsCnt++) {
                                                            Element containerSettingsElement = (Element) containerSettingsChildList.item(containerSettingsCnt);
                                                            if (containerSettingsElement.getNodeName().equals("SubContainerTemplateId")) {
                                                                String subContainerTemplateId = XMLHelper.getCharacterDataFromElement(containerSettingsElement);
                                                                subContainerSettings.setSubContainerTemplateId(Integer.parseInt(subContainerTemplateId));
                                                            } else  if (containerSettingsElement.getNodeName().equals("Data")) {
                                                                NodeList dataChildList = containerSettingsElement.getChildNodes();
                                                                Element dataChildElement = (Element) dataChildList.item(0);
                                                                NodeList tableColumnsChildList = dataChildElement.getChildNodes();
                                                                List<String> tableColumns = new ArrayList<>();
                                                                for (int childCnt = 0; childCnt < tableColumnsChildList.getLength(); childCnt++) {
                                                                    Element settingFieldsElement = (Element) tableColumnsChildList.item(childCnt);
                                                                    if (settingFieldsElement.getNodeName().equals("ColumnName")) {
                                                                        String TableColumnName = getCharacterDataFromElement(settingFieldsElement);
                                                                        tableColumns.add(TableColumnName);
                                                                    }
                                                                }
                                                                subContainerSettings.setSubContainerTableColumns(tableColumns);
                                                            } else if (containerSettingsElement.getNodeName().equals("UISettings")) {
                                                                Element uiSettingsElement = (Element) containerSettingsElement.getChildNodes().item(0);
                                                                NodeList uiSettingsElementChildList = uiSettingsElement.getChildNodes();
                                                                for (int childCnt = 0; childCnt < uiSettingsElementChildList.getLength(); childCnt++) {
                                                                    Element tableSettingsElement = (Element) uiSettingsElementChildList.item(childCnt);
                                                                    if (tableSettingsElement.getNodeName().equals("HeaderSettings")) {
                                                                        NodeList headerSettingsChildList = tableSettingsElement.getChildNodes();
                                                                        SubContainerHeaderSettings subContainerHeaderSettings = new SubContainerHeaderSettings();
                                                                        for (int headerSettingsCnt = 0; headerSettingsCnt < headerSettingsChildList.getLength(); headerSettingsCnt++) {
                                                                            Element headerSettingsElement = (Element) headerSettingsChildList.item(headerSettingsCnt);
                                                                            if (headerSettingsElement.getNodeName().equals("Width")) {
                                                                                String width = XMLHelper.getCharacterDataFromElement(headerSettingsElement);
                                                                                subContainerHeaderSettings.setWidth(Integer.parseInt(width));
                                                                            } else if (headerSettingsElement.getNodeName().equals("Height")) {
                                                                                String height = XMLHelper.getCharacterDataFromElement(headerSettingsElement);
                                                                                subContainerHeaderSettings.setHeight(Integer.parseInt(height));
                                                                            } else if (headerSettingsElement.getNodeName().equals("TextStyle")) {
                                                                                String textStyle = XMLHelper.getCharacterDataFromElement(headerSettingsElement);
                                                                                subContainerHeaderSettings.setTextStyle(textStyle);
                                                                            } else if (headerSettingsElement.getNodeName().equals("TextSize")) {
                                                                                String textSize = XMLHelper.getCharacterDataFromElement(headerSettingsElement);
                                                                                subContainerHeaderSettings.setTextSize(Integer.parseInt(textSize));
                                                                            } else if (headerSettingsElement.getNodeName().equals("TextColor")) {
                                                                                String textColor = XMLHelper.getCharacterDataFromElement(headerSettingsElement);
                                                                                subContainerHeaderSettings.setTextColor(textColor);
                                                                            } else if (headerSettingsElement.getNodeName().equals("Alignment")) {
                                                                                String alignment = XMLHelper.getCharacterDataFromElement(headerSettingsElement);
                                                                                subContainerHeaderSettings.setAlignment(alignment);
                                                                            } else if (headerSettingsElement.getNodeName().equals("BackgroundcolorType")) {
                                                                                String backgroundcolorType = XMLHelper.getCharacterDataFromElement(headerSettingsElement);
                                                                                subContainerHeaderSettings.setBackgroundcolorType(backgroundcolorType);
                                                                            } else if (headerSettingsElement.getNodeName().equals("BackgroundcolorOne")) {
                                                                                String backgroundcolorOne = XMLHelper.getCharacterDataFromElement(headerSettingsElement);
                                                                                subContainerHeaderSettings.setBackgroundcolorOne(backgroundcolorOne);
                                                                            } else if (headerSettingsElement.getNodeName().equals("BackgroundcolorTwo")) {
                                                                                String backgroundcolorTwo = XMLHelper.getCharacterDataFromElement(headerSettingsElement);
                                                                                subContainerHeaderSettings.setBackgroundcolorTwo(backgroundcolorTwo);
                                                                            }
                                                                        }
                                                                        subContainerSettings.setSubContainerHeaderSettings(subContainerHeaderSettings);
                                                                    }else if (tableSettingsElement.getNodeName().equals("BodySettings")) {
                                                                        NodeList subContainerbodySettingsChildList = tableSettingsElement.getChildNodes();
                                                                        SubContainerBodySettings subContainerBodySettings = new SubContainerBodySettings();
                                                                        for (int bodySettingsCnt = 0; bodySettingsCnt < subContainerbodySettingsChildList.getLength(); bodySettingsCnt++) {
                                                                            Element subContainerBodySettingsElement = (Element) subContainerbodySettingsChildList.item(bodySettingsCnt);
                                                                            if (subContainerBodySettingsElement.getNodeName().equals("Height")) {
                                                                                String height = XMLHelper.getCharacterDataFromElement(subContainerBodySettingsElement);
                                                                                subContainerBodySettings.setHeight(Integer.parseInt(height));
                                                                            } else if (subContainerBodySettingsElement.getNodeName().equals("TextStyle")) {
                                                                                String textStyle = XMLHelper.getCharacterDataFromElement(subContainerBodySettingsElement);
                                                                                subContainerBodySettings.setTextStyle(textStyle);
                                                                            } else if (subContainerBodySettingsElement.getNodeName().equals("TextSize")) {
                                                                                String textSize = XMLHelper.getCharacterDataFromElement(subContainerBodySettingsElement);
                                                                                subContainerBodySettings.setTextSize(Integer.parseInt(textSize));
                                                                            } else if (subContainerBodySettingsElement.getNodeName().equals("TextColor")) {
                                                                                String textColor = XMLHelper.getCharacterDataFromElement(subContainerBodySettingsElement);
                                                                                subContainerBodySettings.setTextColor(textColor);
                                                                            } else if (subContainerBodySettingsElement.getNodeName().equals("Alignment")) {
                                                                                String alignment = XMLHelper.getCharacterDataFromElement(subContainerBodySettingsElement);
                                                                                subContainerBodySettings.setAlignment(alignment);
                                                                            } else if (subContainerBodySettingsElement.getNodeName().equals("BackgroundcolorType")) {
                                                                                String backgroundcolorType = XMLHelper.getCharacterDataFromElement(subContainerBodySettingsElement);
                                                                                subContainerBodySettings.setBackgroundcolorType(backgroundcolorType);
                                                                            } else if (subContainerBodySettingsElement.getNodeName().equals("BackgroundcolorOne")) {
                                                                                String backgroundcolorOne = XMLHelper.getCharacterDataFromElement(subContainerBodySettingsElement);
                                                                                subContainerBodySettings.setBackgroundcolorOne(backgroundcolorOne);
                                                                            } else if (subContainerBodySettingsElement.getNodeName().equals("BackgroundcolorTwo")) {
                                                                                String backgroundcolorTwo = XMLHelper.getCharacterDataFromElement(subContainerBodySettingsElement);
                                                                                subContainerBodySettings.setBackgroundcolorTwo(backgroundcolorTwo);
                                                                            } else if (subContainerBodySettingsElement.getNodeName().equals("BorderColor")) {
                                                                                String borderColor = XMLHelper.getCharacterDataFromElement(subContainerBodySettingsElement);
                                                                                subContainerBodySettings.setBorderColor(borderColor);
                                                                            }
                                                                        }
                                                                        subContainerSettings.setSubContainerBodySettings(subContainerBodySettings);
                                                                    }
                                                                }
                                                            }
                                                        }
                                                            subContainerSettingsList.add(subContainerSettings);
                                                        }dataManagementOptions.getDetailedPageDetails().getBodySettings().getLeftLayoutSettings().setSubContainerSettings(subContainerSettingsList);}
                                                }
                                            }else  if (bodySettingsElement.getNodeName().equals("RightLayoutSettings")) {
                                                dataManagementOptions.getDetailedPageDetails().getBodySettings().getRightLayoutSettings().setRightLayout(Boolean.parseBoolean(bodySettingsElement.getAttribute("isRightLayout")));
                                                NodeList rightLayoutChildList = bodySettingsElement.getChildNodes();
                                                for (int rightLayoutChildListCnt = 0; rightLayoutChildListCnt < rightLayoutChildList.getLength(); rightLayoutChildListCnt++) {
                                                    Element tableColumnsChildElement = (Element) rightLayoutChildList.item(rightLayoutChildListCnt);
                                                NodeList tableColumnsChildList = tableColumnsChildElement.getChildNodes();
                                                List<String> tableColumns = new ArrayList<>();
                                                for (int childCnt = 0; childCnt < tableColumnsChildList.getLength(); childCnt++) {
                                                    Element settingFieldsElement = (Element) tableColumnsChildList.item(childCnt);
                                                    if (settingFieldsElement.getNodeName().equals("ColumnName")) {
                                                        String TableColumnName = getCharacterDataFromElement(settingFieldsElement);
                                                        tableColumns.add(TableColumnName);
                                                    }
                                                }
                                                dataManagementOptions.getDetailedPageDetails().getBodySettings().getRightLayoutSettings().setTableColumns(tableColumns);
                                            }}
                                        }
                                    }
                                }

                            }}} else if (advanceManagementElement.getNodeName().trim().equalsIgnoreCase("EditColumns")) {
                                NodeList columnsChildList = advanceManagementElement.getChildNodes();
                                List<EditOrViewColumns> editOrViewColumnsList = new ArrayList<>();
                                for (int childCnt = 0; childCnt < columnsChildList.getLength(); childCnt++) {
                                    EditOrViewColumns editOrViewColumns = new EditOrViewColumns();
                                    Element settingFieldsElement = (Element) columnsChildList.item(childCnt);
                                    if (settingFieldsElement.getNodeName().equals("ColumnName")) {
                                        String TableColumnName = getCharacterDataFromElement(settingFieldsElement);
                                        editOrViewColumns.setColumnName(TableColumnName);
                                    }
                                    editOrViewColumns.setColumnType(settingFieldsElement.getAttribute("type"));
                                    editOrViewColumns.setFormName(settingFieldsElement.getAttribute("subformName"));
                                    editOrViewColumnsList.add(editOrViewColumns);
                                }
                                dataManagementOptions.setEditColumns(editOrViewColumnsList);

                            } else if (advanceManagementElement.getNodeName().equals("EnableViewData")) {
                                String enableViewData = XMLHelper.getCharacterDataFromElement(advanceManagementElement);
                                dataManagementOptions.setEnableViewData(Boolean.parseBoolean(enableViewData));
                            } else if (advanceManagementElement.getNodeName().equalsIgnoreCase("IndexPageColumnsOrder")) {
                                String indexPageColumnsOrder = XMLHelper.getCharacterDataFromElement(advanceManagementElement);
                                dataManagementOptions.setIndexPageColumnsOrder(indexPageColumnsOrder);
                            } else if (advanceManagementElement.getNodeName().equalsIgnoreCase("Order")) {
                                String order = XMLHelper.getCharacterDataFromElement(advanceManagementElement);
                                dataManagementOptions.setOrder(order);
                            } else if (advanceManagementElement.getNodeName().equals("EnableEditData")) {
                                String enableEditData = XMLHelper.getCharacterDataFromElement(advanceManagementElement);

                                dataManagementOptions.setEnableEditData(Boolean.parseBoolean(enableEditData));
                            } else if (advanceManagementElement.getNodeName().equals("EnableDeleteData")) {
                                String enableDeleteData = XMLHelper.getCharacterDataFromElement(advanceManagementElement);
                                dataManagementOptions.setEnableDeleteData(Boolean.parseBoolean(enableDeleteData));
                            }else if (advanceManagementElement.getNodeName().equals("RecordInsertionType")) {
                                String allowOnlyOneRecord = XMLHelper.getCharacterDataFromElement(advanceManagementElement);
                                dataManagementOptions.setRecordInsertionType(allowOnlyOneRecord);
                            } else if (advanceManagementElement.getNodeName().equals("LazyLoading")) {
                                String lazyLoading = XMLHelper.getCharacterDataFromElement(advanceManagementElement);
                                dataManagementOptions.setLazyLoadingEnabled(Boolean.parseBoolean(lazyLoading));
                            } else if (advanceManagementElement.getNodeName().equals("LazyLoadingThreshold")) {
                                if (dataManagementOptions.isLazyLoadingEnabled()) {
                                    String lazyLoadingThreshold = XMLHelper.getCharacterDataFromElement(advanceManagementElement);
                                    dataManagementOptions.setLazyLoadingThreshold(lazyLoadingThreshold);
                                }
                            } else if (advanceManagementElement.getNodeName().equals("CaptionForAdd")) {
                                String captionForAdd = XMLHelper.getCharacterDataFromElement(advanceManagementElement);
                                dataManagementOptions.setCaptionForAdd(captionForAdd);

                            } else if (advanceManagementElement.getNodeName().equals("FetchData")) {
                                String fetchData = XMLHelper.getCharacterDataFromElement(advanceManagementElement);
                                dataManagementOptions.setFetchData(fetchData);
                            } else if (advanceManagementElement.getNodeName().trim().equalsIgnoreCase("InputParameters")) {
                                dataManagementOptions.setFilterColumns(filterColumns((Element) nNode));
                            } else if (advanceManagementElement.getNodeName().equals("ReportDisplayType")) {
                                String ReportDisplayType = getCharacterDataFromElement(advanceManagementElement);
                                if (ReportDisplayType != null && ReportDisplayType != "" && !ReportDisplayType.equalsIgnoreCase("false")) {
                                    dataManagementOptions.setReportDisplayType(Integer.parseInt(ReportDisplayType));
                                }
                            }
                        }
                        break;*/
                    }
                }

            }
        } catch (Exception e) {
            System.out.println("Exception:AdvanceManagement in" + ":" + e.getMessage());
            dataManagementOptions = null;
        }
        return dataManagementOptions;
    }
    public static DataManagementOptions getAdvancedSettings(Node nNode) {
        DataManagementOptions dataManagementOptions =new DataManagementOptions();
        NodeList advanceManagementChildList = nNode.getChildNodes();
        for (int advanceManagementCnt = 0; advanceManagementCnt < advanceManagementChildList.getLength(); advanceManagementCnt++) {
            Element advanceManagementElement = (Element) advanceManagementChildList.item(advanceManagementCnt);
            if (advanceManagementElement.getNodeName().equals("MobileSettings")) {
                NodeList mobileSettingsChildList = advanceManagementElement.getChildNodes();
                for (int mobileSettingsCnt = 0; mobileSettingsCnt < mobileSettingsChildList.getLength(); mobileSettingsCnt++) {
                    Element mobileSettingsElement = (Element) mobileSettingsChildList.item(mobileSettingsCnt);
                    if (mobileSettingsElement.getNodeName().equals("IndexPageDetails")) {
                        dataManagementOptions.getIndexPageDetails().setIndexPage(Boolean.parseBoolean(mobileSettingsElement.getAttribute("isIndexPage")));
                        NodeList IndexPageDetailsChildList = mobileSettingsElement.getChildNodes();
                        for (int IndexPageDetailsCnt = 0; IndexPageDetailsCnt < IndexPageDetailsChildList.getLength(); IndexPageDetailsCnt++) {
                            Element IndexPageDetailsElement = (Element) IndexPageDetailsChildList.item(IndexPageDetailsCnt);
                            if (IndexPageDetailsElement.getNodeName().equals("IndexPageTemplateId")) {
                                String indexPageTemplateId = XMLHelper.getCharacterDataFromElement(IndexPageDetailsElement);
                                dataManagementOptions.getIndexPageDetails().setIndexPageTemplateId(Integer.parseInt(indexPageTemplateId));
                            }
                            if (IndexPageDetailsElement.getNodeName().equals("Data")) {
                                NodeList dataChildList = IndexPageDetailsElement.getChildNodes();
                                for (int dataChildListCnt = 0; dataChildListCnt < dataChildList.getLength(); dataChildListCnt++) {
                                    Element dataElement = (Element) dataChildList.item(dataChildListCnt);
                                    if (dataElement.getNodeName().equals("PageTitle")) {
                                        String pageTitle = XMLHelper.getCharacterDataFromElement(dataElement);
                                        dataManagementOptions.getIndexPageDetails().setPageTitle(pageTitle);
                                    } else if (dataElement.getNodeName().equals("Image")) {
                                        String image = XMLHelper.getCharacterDataFromElement(dataElement);
                                        dataManagementOptions.getIndexPageDetails().setImage(image);
                                    } else if (dataElement.getNodeName().equals("ProfileImage")) {
                                        String profileImage = XMLHelper.getCharacterDataFromElement(dataElement);
                                        dataManagementOptions.getIndexPageDetails().setProfileImage(profileImage);
                                    } else if (dataElement.getNodeName().equals("Header")) {
                                        String header = XMLHelper.getCharacterDataFromElement(dataElement);
                                        dataManagementOptions.getIndexPageDetails().setHeader(header);
                                    } else if (dataElement.getNodeName().equals("SubHeader")) {
                                        String subHeader = XMLHelper.getCharacterDataFromElement(dataElement);
                                        dataManagementOptions.getIndexPageDetails().setSubHeader(subHeader);
                                    } else if (dataElement.getNodeName().equalsIgnoreCase("Description")) {
                                        List<String> descriptionItems = new ArrayList<>();
                                        NodeList columnsChildList = dataElement.getChildNodes();
                                        for (int childCnt = 0; childCnt < columnsChildList.getLength(); childCnt++) {
                                            Element descriptionElement = (Element) columnsChildList.item(childCnt);
                                            if (descriptionElement.getNodeName().equals("DescriptionItem")) {
                                                String columnName = getCharacterDataFromElement(descriptionElement);
                                                descriptionItems.add(columnName);
                                            }
                                        }
                                        dataManagementOptions.getIndexPageDetails().setDescriptionList(descriptionItems);
                                    }
                                }
                            } else if (IndexPageDetailsElement.getNodeName().equals("UISettings")) {
                                NodeList UISettingsChildList = IndexPageDetailsElement.getChildNodes();
                                for (int UISettingsCnt = 0; UISettingsCnt < UISettingsChildList.getLength(); UISettingsCnt++) {
                                    Element UISettingsElement = (Element) UISettingsChildList.item(UISettingsCnt);
                                    if (UISettingsElement.getNodeName().equals("HeaderFontSize")) {
                                        String headerFontSize = XMLHelper.getCharacterDataFromElement(UISettingsElement);
                                        dataManagementOptions.getIndexPageDetails().getIndexPageUISettings().setHeaderFontSize(headerFontSize);
                                    } else if (UISettingsElement.getNodeName().equals("HeaderFontStyle")) {
                                        String headerFontStyle = XMLHelper.getCharacterDataFromElement(UISettingsElement);
                                        dataManagementOptions.getIndexPageDetails().getIndexPageUISettings().setHeaderFontStyle(headerFontStyle);
                                    } else if (UISettingsElement.getNodeName().equals("HeaderFontColor")) {
                                        String headerFontColor = XMLHelper.getCharacterDataFromElement(UISettingsElement);
                                        dataManagementOptions.getIndexPageDetails().getIndexPageUISettings().setHeaderFontColor(headerFontColor);
                                    } else if (UISettingsElement.getNodeName().equals("SubHeaderFontSize")) {
                                        String subHeaderFontSize = XMLHelper.getCharacterDataFromElement(UISettingsElement);
                                        dataManagementOptions.getIndexPageDetails().getIndexPageUISettings().setSubHeaderFontSize(subHeaderFontSize);
                                    } else if (UISettingsElement.getNodeName().equals("SubHeaderFontStyle")) {
                                        String subHeaderFontStyle = XMLHelper.getCharacterDataFromElement(UISettingsElement);
                                        dataManagementOptions.getIndexPageDetails().getIndexPageUISettings().setSubHeaderFontStyle(subHeaderFontStyle);
                                    } else if (UISettingsElement.getNodeName().equals("SubHeaderFontColor")) {
                                        String subHeaderFontColor = XMLHelper.getCharacterDataFromElement(UISettingsElement);
                                        dataManagementOptions.getIndexPageDetails().getIndexPageUISettings().setSubHeaderFontColor(subHeaderFontColor);
                                    } else if (UISettingsElement.getNodeName().equals("DescriptionFontSize")) {
                                        String descriptionFontSize = XMLHelper.getCharacterDataFromElement(UISettingsElement);
                                        dataManagementOptions.getIndexPageDetails().getIndexPageUISettings().setDescriptionFontSize(descriptionFontSize);
                                    } else if (UISettingsElement.getNodeName().equals("DescriptionFontStyle")) {
                                        String descriptionFontStyle = XMLHelper.getCharacterDataFromElement(UISettingsElement);
                                        dataManagementOptions.getIndexPageDetails().getIndexPageUISettings().setDescriptionFontStyle(descriptionFontStyle);
                                    } else if (UISettingsElement.getNodeName().equals("DescriptionFontColor")) {
                                        String descriptionFontColor = XMLHelper.getCharacterDataFromElement(UISettingsElement);
                                        dataManagementOptions.getIndexPageDetails().getIndexPageUISettings().setDescriptionFontColor(descriptionFontColor);
                                    }
                                }
                            }
                        }
                    } else if (mobileSettingsElement.getNodeName().equals("DetailedPageDetails")) {
                        dataManagementOptions.getDetailedPageDetails().setDetailPage(Boolean.parseBoolean(mobileSettingsElement.getAttribute("isDetailPage")));
                        NodeList detailedPageDetailsChildList = mobileSettingsElement.getChildNodes();
                        for (int detailedPageDetailsCnt = 0; detailedPageDetailsCnt < detailedPageDetailsChildList.getLength(); detailedPageDetailsCnt++) {
                            Element detailedPageDetailsElement = (Element) detailedPageDetailsChildList.item(detailedPageDetailsCnt);
                            if (detailedPageDetailsElement.getNodeName().equals("DetailedPageTemplateId")) {
                                String detailedPageTemplateId = XMLHelper.getCharacterDataFromElement(detailedPageDetailsElement);
                                dataManagementOptions.getDetailedPageDetails().setDetailedPageTemplateId(Integer.parseInt(detailedPageTemplateId));
                            } else if (detailedPageDetailsElement.getNodeName().equals("HeaderSettings")) {
                                dataManagementOptions.getDetailedPageDetails().getDetailedPageHeaderSettings().setHeaderLayout(Boolean.parseBoolean(detailedPageDetailsElement.getAttribute("isHeaderLayout")));
                                NodeList HeaderSettingsChildList = detailedPageDetailsElement.getChildNodes();
                                for (int HeaderSettingsChildListCnt = 0; HeaderSettingsChildListCnt < HeaderSettingsChildList.getLength(); HeaderSettingsChildListCnt++) {
                                    Element headerSettingElement = (Element) HeaderSettingsChildList.item(HeaderSettingsChildListCnt);
                                    if (headerSettingElement.getNodeName().equals("Data")) {
                                        NodeList dataChildList = headerSettingElement.getChildNodes();
                                        Data data = new Data();
                                        for (int dataChildListCnt = 0; dataChildListCnt < dataChildList.getLength(); dataChildListCnt++) {
                                            Element dataElement = (Element) dataChildList.item(dataChildListCnt);
                                            if (dataElement.getNodeName().equals("PageTitle")) {
                                                String pageTitle = XMLHelper.getCharacterDataFromElement(dataElement);
                                                data.setPageTitle(pageTitle);
                                            } else if (dataElement.getNodeName().equals("Image")) {
                                                String image = XMLHelper.getCharacterDataFromElement(dataElement);
                                                data.setImage(image);
                                            } else if (dataElement.getNodeName().equals("Header")) {
                                                String header = XMLHelper.getCharacterDataFromElement(dataElement);
                                                data.setHeader(header);
                                            } else if (dataElement.getNodeName().equals("SubHeader")) {
                                                String subHeader = XMLHelper.getCharacterDataFromElement(dataElement);
                                                data.setSubHeader(subHeader);
                                            } else if (dataElement.getNodeName().equalsIgnoreCase("Description")) {
                                                List<String> descriptionItems = new ArrayList<>();
                                                NodeList columnsChildList = dataElement.getChildNodes();
                                                for (int childCnt = 0; childCnt < columnsChildList.getLength(); childCnt++) {
                                                    Element descriptionElement = (Element) columnsChildList.item(childCnt);
                                                    if (descriptionElement.getNodeName().equals("DescriptionItem")) {
                                                        String columnName = getCharacterDataFromElement(descriptionElement);
                                                        descriptionItems.add(columnName);
                                                    }
                                                }
                                                data.setDescriptionList(descriptionItems);
                                            } else if (dataElement.getNodeName().equals("CardOneImage")) {
                                                String cardOneImage = XMLHelper.getCharacterDataFromElement(dataElement);
                                                data.setCardOneImage(cardOneImage);
                                            } else if (dataElement.getNodeName().equals("CardOneLable")) {
                                                String cardOneLable = XMLHelper.getCharacterDataFromElement(dataElement);
                                                data.setCardOneLable(cardOneLable);
                                            } else if (dataElement.getNodeName().equals("CardOneValue")) {
                                                String cardOneValue = XMLHelper.getCharacterDataFromElement(dataElement);
                                                data.setCardOneValue(cardOneValue);
                                            } else if (dataElement.getNodeName().equals("CardTwoImage")) {
                                                String cardTwoImage = XMLHelper.getCharacterDataFromElement(dataElement);
                                                data.setCardTwoImage(cardTwoImage);
                                            } else if (dataElement.getNodeName().equals("CardTwoLable")) {
                                                String cardTwoLable = XMLHelper.getCharacterDataFromElement(dataElement);
                                                data.setCardTwoLable(cardTwoLable);
                                            } else if (dataElement.getNodeName().equals("CardTwoValue")) {
                                                String cardTwoValue = XMLHelper.getCharacterDataFromElement(dataElement);
                                                data.setCardTwoValue(cardTwoValue);
                                            }else if (dataElement.getNodeName().equals("ProfileImage")) {
                                                String profileImage = XMLHelper.getCharacterDataFromElement(dataElement);
                                                data.setProfileImage(profileImage);
                                            }else if (dataElement.getNodeName().equals("Date")) {
                                                String date = XMLHelper.getCharacterDataFromElement(dataElement);
                                                data.setDate(date);
                                            }else if (dataElement.getNodeName().equals("Title")) {
                                                String title = XMLHelper.getCharacterDataFromElement(dataElement);
                                                data.setTitle(title);
                                            }
                                        }
                                        dataManagementOptions.getDetailedPageDetails().getDetailedPageHeaderSettings().setData(data);
                                    } else if (headerSettingElement.getNodeName().equals("UISettings")) {
                                        NodeList UISettingsChildList = headerSettingElement.getChildNodes();
                                        DetailedPageUISettings detailedPageUISettings = new DetailedPageUISettings();
                                        for (int UISettingsCnt = 0; UISettingsCnt < UISettingsChildList.getLength(); UISettingsCnt++) {
                                            Element UISettingsElement = (Element) UISettingsChildList.item(UISettingsCnt);
                                            if (UISettingsElement.getNodeName().equals("HeaderFontSize")) {
                                                String headerFontSize = XMLHelper.getCharacterDataFromElement(UISettingsElement);
                                                detailedPageUISettings.setHeaderFontSize(headerFontSize);
                                            } else if (UISettingsElement.getNodeName().equals("HeaderFontStyle")) {
                                                String headerFontStyle = XMLHelper.getCharacterDataFromElement(UISettingsElement);
                                                detailedPageUISettings.setHeaderFontStyle(headerFontStyle);
                                            } else if (UISettingsElement.getNodeName().equals("HeaderFontColor")) {
                                                String headerFontColor = XMLHelper.getCharacterDataFromElement(UISettingsElement);
                                                detailedPageUISettings.setHeaderFontColor(headerFontColor);
                                            } else if (UISettingsElement.getNodeName().equals("SubHeaderFontSize")) {
                                                String subHeaderFontSize = XMLHelper.getCharacterDataFromElement(UISettingsElement);
                                                detailedPageUISettings.setSubHeaderFontSize(subHeaderFontSize);
                                            } else if (UISettingsElement.getNodeName().equals("SubHeaderFontStyle")) {
                                                String subHeaderFontStyle = XMLHelper.getCharacterDataFromElement(UISettingsElement);
                                                detailedPageUISettings.setSubHeaderFontStyle(subHeaderFontStyle);
                                            } else if (UISettingsElement.getNodeName().equals("SubHeaderFontColor")) {
                                                String subHeaderFontColor = XMLHelper.getCharacterDataFromElement(UISettingsElement);
                                                detailedPageUISettings.setSubHeaderFontColor(subHeaderFontColor);
                                            } else if (UISettingsElement.getNodeName().equals("DescriptionFontSize")) {
                                                String descriptionFontSize = XMLHelper.getCharacterDataFromElement(UISettingsElement);
                                                detailedPageUISettings.setDescriptionFontSize(descriptionFontSize);
                                            } else if (UISettingsElement.getNodeName().equals("DescriptionFontStyle")) {
                                                String descriptionFontStyle = XMLHelper.getCharacterDataFromElement(UISettingsElement);
                                                detailedPageUISettings.setDescriptionFontStyle(descriptionFontStyle);
                                            } else if (UISettingsElement.getNodeName().equals("DescriptionFontColor")) {
                                                String descriptionFontColor = XMLHelper.getCharacterDataFromElement(UISettingsElement);
                                                detailedPageUISettings.setDescriptionFontColor(descriptionFontColor);
                                            } else if (UISettingsElement.getNodeName().equals("BackgroundColor")) {
                                                String backgroundColor = XMLHelper.getCharacterDataFromElement(UISettingsElement);
                                                detailedPageUISettings.setBackgroundColor(backgroundColor);
                                            } else if (UISettingsElement.getNodeName().equals("CardOneLableFontColor")) {
                                                String cardOneLableFontColor = XMLHelper.getCharacterDataFromElement(UISettingsElement);
                                                detailedPageUISettings.setCardOneLabelFontColor(cardOneLableFontColor);
                                            } else if (UISettingsElement.getNodeName().equals("CardOneLableFontSize")) {
                                                String cardOneLableFontSize = XMLHelper.getCharacterDataFromElement(UISettingsElement);
                                                detailedPageUISettings.setCardOneLabelFontSize(cardOneLableFontSize);
                                            } else if (UISettingsElement.getNodeName().equals("CardOneValueFontColor")) {
                                                String cardOneValueFontColor = XMLHelper.getCharacterDataFromElement(UISettingsElement);
                                                detailedPageUISettings.setCardOneValueFontColor(cardOneValueFontColor);
                                            } else if (UISettingsElement.getNodeName().equals("CardOneValueFontSize")) {
                                                String cardOneValueFontSize = XMLHelper.getCharacterDataFromElement(UISettingsElement);
                                                detailedPageUISettings.setCardOneValueFontSize(cardOneValueFontSize);
                                            } else if (UISettingsElement.getNodeName().equals("CardOneBackgroundColor")) {
                                                String cardOneBackgroundColor = XMLHelper.getCharacterDataFromElement(UISettingsElement);
                                                detailedPageUISettings.setCardOneBackgroundColor(cardOneBackgroundColor);
                                            } else if (UISettingsElement.getNodeName().equals("CardTwoLableFontColor")) {
                                                String cardTwoLableFontColor = XMLHelper.getCharacterDataFromElement(UISettingsElement);
                                                detailedPageUISettings.setCardTwoLabelFontColor(cardTwoLableFontColor);
                                            } else if (UISettingsElement.getNodeName().equals("CardTwoLableFontSize")) {
                                                String cardTwoLableFontSize = XMLHelper.getCharacterDataFromElement(UISettingsElement);
                                                detailedPageUISettings.setCardTwoLabelFontSize(cardTwoLableFontSize);
                                            } else if (UISettingsElement.getNodeName().equals("CardTwoValueFontColor")) {
                                                String cardTwoValueFontColor = XMLHelper.getCharacterDataFromElement(UISettingsElement);
                                                detailedPageUISettings.setCardTwoValueFontColor(cardTwoValueFontColor);
                                            } else if (UISettingsElement.getNodeName().equals("CardTwoValueFontSize")) {
                                                String cardTwoValueFontSize = XMLHelper.getCharacterDataFromElement(UISettingsElement);
                                                detailedPageUISettings.setCardTwoValueFontSize(cardTwoValueFontSize);
                                            } else if (UISettingsElement.getNodeName().equals("CardTwoBackgroundColor")) {
                                                String cardTwoBackgroundColor = XMLHelper.getCharacterDataFromElement(UISettingsElement);
                                                detailedPageUISettings.setCardTwoBackgroundColor(cardTwoBackgroundColor);
                                            }
                                        }
                                        dataManagementOptions.getDetailedPageDetails().getDetailedPageHeaderSettings().setDetailedPageUISettings(detailedPageUISettings);
                                    }
                                }
                            } else if (detailedPageDetailsElement.getNodeName().equals("BodySettings")) {
                                NodeList bodySettingsChildList = detailedPageDetailsElement.getChildNodes();
                                for (int bodySettingsChildListCnt = 0; bodySettingsChildListCnt < bodySettingsChildList.getLength(); bodySettingsChildListCnt++) {
                                    Element bodySettingsElement = (Element) bodySettingsChildList.item(bodySettingsChildListCnt);
                                    if (bodySettingsElement.getNodeName().equals("LeftLayoutSettings")) {
                                        if(!dataManagementOptions.getDetailedPageDetails().getDetailedPageHeaderSettings().isHeaderLayout()){
                                            dataManagementOptions.getDetailedPageDetails().getBodySettings().getLeftLayoutSettings().setDefaultPageTitle(bodySettingsElement.getAttribute("defaultPageTitle"));
                                        }
                                        dataManagementOptions.getDetailedPageDetails().getBodySettings().getLeftLayoutSettings().setLeftLayout(Boolean.parseBoolean(bodySettingsElement.getAttribute("isLeftLayout")));
                                        NodeList LeftLayoutSettingsChildList = bodySettingsElement.getChildNodes();
                                        for (int LeftLayoutSettingsChildListCnt = 0; LeftLayoutSettingsChildListCnt < LeftLayoutSettingsChildList.getLength(); LeftLayoutSettingsChildListCnt++) {
                                            Element LeftLayoutSettingsElement = (Element) LeftLayoutSettingsChildList.item(LeftLayoutSettingsChildListCnt);
                                            if (LeftLayoutSettingsElement.getNodeName().equals("MainContainerSettings")) {
                                                NodeList MainContainerSettingsChildList = LeftLayoutSettingsElement.getChildNodes();
                                                for (int MainContainerSettingsChildListCnt = 0; MainContainerSettingsChildListCnt < MainContainerSettingsChildList.getLength(); MainContainerSettingsChildListCnt++) {
                                                    Element MainContainerSettingsChildElement = (Element) MainContainerSettingsChildList.item(MainContainerSettingsChildListCnt);
                                                    if (MainContainerSettingsChildElement.getNodeName().equals("MainContainerTemplateId")) {
                                                        String mainContainerTemplateId = XMLHelper.getCharacterDataFromElement(MainContainerSettingsChildElement);
                                                        dataManagementOptions.getDetailedPageDetails().getBodySettings().getLeftLayoutSettings().getMainContainerSettings().setMainContainerTemplateId(Integer.parseInt(mainContainerTemplateId));
                                                    } else if (MainContainerSettingsChildElement.getNodeName().equals("Data")) {
                                                        NodeList dataChildList = MainContainerSettingsChildElement.getChildNodes();
                                                        Element dataChildElement = (Element) dataChildList.item(0);
                                                        NodeList tableColumnsChildList = dataChildElement.getChildNodes();
                                                        List<String> tableColumns = new ArrayList<>();
                                                        for (int childCnt = 0; childCnt < tableColumnsChildList.getLength(); childCnt++) {
                                                            Element settingFieldsElement = (Element) tableColumnsChildList.item(childCnt);
                                                            if (settingFieldsElement.getNodeName().equals("ColumnName")) {
                                                                String TableColumnName = getCharacterDataFromElement(settingFieldsElement);
                                                                tableColumns.add(TableColumnName);
                                                            }
                                                        }
                                                        dataManagementOptions.getDetailedPageDetails().getBodySettings().getLeftLayoutSettings().getMainContainerSettings().setTableColumns(tableColumns);
                                                    } else if (MainContainerSettingsChildElement.getNodeName().equals("UISettings")) {
                                                        NodeList UISettingsChildList = MainContainerSettingsChildElement.getChildNodes();
                                                        MainContainerUISettings mainContainerUISettings = new MainContainerUISettings();
                                                        for (int UISettingsCnt = 0; UISettingsCnt < UISettingsChildList.getLength(); UISettingsCnt++) {
                                                            Element UISettingsElement = (Element) UISettingsChildList.item(UISettingsCnt);
                                                            if (UISettingsElement.getNodeName().equals("LableFontSize")) {
                                                                String labelFontSize = XMLHelper.getCharacterDataFromElement(UISettingsElement);
                                                                mainContainerUISettings.setLabelFontSize(Integer.parseInt(labelFontSize));
                                                            } else if (UISettingsElement.getNodeName().equals("LableFontColor")) {
                                                                String labelFontColor = XMLHelper.getCharacterDataFromElement(UISettingsElement);
                                                                mainContainerUISettings.setLabelFontColor(labelFontColor);
                                                            } else if (UISettingsElement.getNodeName().equals("ValueFontSize")) {
                                                                String valueFontSize = XMLHelper.getCharacterDataFromElement(UISettingsElement);
                                                                mainContainerUISettings.setValueFontSize(Integer.parseInt(valueFontSize));
                                                            } else if (UISettingsElement.getNodeName().equals("ValueFontColor")) {
                                                                String valueFontColor = XMLHelper.getCharacterDataFromElement(UISettingsElement);
                                                                mainContainerUISettings.setValueFontColor(valueFontColor);
                                                            } else if (UISettingsElement.getNodeName().equals("BorderStyle")) {
                                                                String borderStyle = XMLHelper.getCharacterDataFromElement(UISettingsElement);
                                                                mainContainerUISettings.setBorderStyle(borderStyle);
                                                            } else if (UISettingsElement.getNodeName().equals("BorderColor")) {
                                                                String borderColor = XMLHelper.getCharacterDataFromElement(UISettingsElement);
                                                                mainContainerUISettings.setBorderColor(borderColor);
                                                            } else if (UISettingsElement.getNodeName().equals("BackgroundColor")) {
                                                                String backgroundColor = XMLHelper.getCharacterDataFromElement(UISettingsElement);
                                                                mainContainerUISettings.setBackgroundColor(backgroundColor);
                                                            } else if (UISettingsElement.getNodeName().equals("Alignment")) {
                                                                String alignment = XMLHelper.getCharacterDataFromElement(UISettingsElement);
                                                                mainContainerUISettings.setAlignment(alignment);
                                                            } else if (UISettingsElement.getNodeName().equals("ActiveColor")) {
                                                                String activeColor = XMLHelper.getCharacterDataFromElement(UISettingsElement);
                                                                mainContainerUISettings.setActiveColor(activeColor);
                                                            }
                                                        }
                                                        dataManagementOptions.getDetailedPageDetails().getBodySettings().getLeftLayoutSettings().getMainContainerSettings().setMainContainerUISettings(mainContainerUISettings);
                                                    }
                                                }
                                            }else if (LeftLayoutSettingsElement.getNodeName().equals("SubContainerSettings")) {
                                                List<SubContainerSettings> subContainerSettingsList = new ArrayList<>();
                                                NodeList subContainerSettingsChildList = LeftLayoutSettingsElement.getChildNodes();
                                                for (int subContainerSettingsCnt = 0; subContainerSettingsCnt < subContainerSettingsChildList.getLength(); subContainerSettingsCnt++) {
                                                    SubContainerSettings subContainerSettings = new SubContainerSettings();
                                                    Element subContainerSettingsElement = (Element) subContainerSettingsChildList.item(subContainerSettingsCnt);
                                                    subContainerSettings.setSubControlName(subContainerSettingsElement.getAttribute("SubControlName"));
                                                    NodeList containerSettingsChildList = subContainerSettingsElement.getChildNodes();
                                                    for (int containerSettingsCnt = 0; containerSettingsCnt < containerSettingsChildList.getLength(); containerSettingsCnt++) {
                                                        Element containerSettingsElement = (Element) containerSettingsChildList.item(containerSettingsCnt);
                                                        if (containerSettingsElement.getNodeName().equals("SubContainerTemplateId")) {
                                                            String subContainerTemplateId = XMLHelper.getCharacterDataFromElement(containerSettingsElement);
                                                            subContainerSettings.setSubContainerTemplateId(Integer.parseInt(subContainerTemplateId));
                                                        } else  if (containerSettingsElement.getNodeName().equals("Data")) {
                                                            NodeList dataChildList = containerSettingsElement.getChildNodes();
                                                            Element dataChildElement = (Element) dataChildList.item(0);
                                                            NodeList tableColumnsChildList = dataChildElement.getChildNodes();
                                                            List<String> tableColumns = new ArrayList<>();
                                                            for (int childCnt = 0; childCnt < tableColumnsChildList.getLength(); childCnt++) {
                                                                Element settingFieldsElement = (Element) tableColumnsChildList.item(childCnt);
                                                                if (settingFieldsElement.getNodeName().equals("ColumnName")) {
                                                                    String TableColumnName = getCharacterDataFromElement(settingFieldsElement);
                                                                    tableColumns.add(TableColumnName);
                                                                }
                                                            }
                                                            subContainerSettings.setSubContainerTableColumns(tableColumns);
                                                        } else if (containerSettingsElement.getNodeName().equals("UISettings")) {
                                                            Element uiSettingsElement = (Element) containerSettingsElement.getChildNodes().item(0);
                                                            NodeList uiSettingsElementChildList = uiSettingsElement.getChildNodes();
                                                            for (int childCnt = 0; childCnt < uiSettingsElementChildList.getLength(); childCnt++) {
                                                                Element tableSettingsElement = (Element) uiSettingsElementChildList.item(childCnt);
                                                                if (tableSettingsElement.getNodeName().equals("HeaderSettings")) {
                                                                    NodeList headerSettingsChildList = tableSettingsElement.getChildNodes();
                                                                    SubContainerHeaderSettings subContainerHeaderSettings = new SubContainerHeaderSettings();
                                                                    for (int headerSettingsCnt = 0; headerSettingsCnt < headerSettingsChildList.getLength(); headerSettingsCnt++) {
                                                                        Element headerSettingsElement = (Element) headerSettingsChildList.item(headerSettingsCnt);
                                                                        if (headerSettingsElement.getNodeName().equals("Width")) {
                                                                            String width = XMLHelper.getCharacterDataFromElement(headerSettingsElement);
                                                                            subContainerHeaderSettings.setWidth(Integer.parseInt(width));
                                                                        } else if (headerSettingsElement.getNodeName().equals("Height")) {
                                                                            String height = XMLHelper.getCharacterDataFromElement(headerSettingsElement);
                                                                            subContainerHeaderSettings.setHeight(Integer.parseInt(height));
                                                                        } else if (headerSettingsElement.getNodeName().equals("TextStyle")) {
                                                                            String textStyle = XMLHelper.getCharacterDataFromElement(headerSettingsElement);
                                                                            subContainerHeaderSettings.setTextStyle(textStyle);
                                                                        } else if (headerSettingsElement.getNodeName().equals("TextSize")) {
                                                                            String textSize = XMLHelper.getCharacterDataFromElement(headerSettingsElement);
                                                                            subContainerHeaderSettings.setTextSize(Integer.parseInt(textSize));
                                                                        } else if (headerSettingsElement.getNodeName().equals("TextColor")) {
                                                                            String textColor = XMLHelper.getCharacterDataFromElement(headerSettingsElement);
                                                                            subContainerHeaderSettings.setTextColor(textColor);
                                                                        } else if (headerSettingsElement.getNodeName().equals("Alignment")) {
                                                                            String alignment = XMLHelper.getCharacterDataFromElement(headerSettingsElement);
                                                                            subContainerHeaderSettings.setAlignment(alignment);
                                                                        } else if (headerSettingsElement.getNodeName().equals("BackgroundcolorType")) {
                                                                            String backgroundcolorType = XMLHelper.getCharacterDataFromElement(headerSettingsElement);
                                                                            subContainerHeaderSettings.setBackgroundcolorType(backgroundcolorType);
                                                                        } else if (headerSettingsElement.getNodeName().equals("BackgroundcolorOne")) {
                                                                            String backgroundcolorOne = XMLHelper.getCharacterDataFromElement(headerSettingsElement);
                                                                            subContainerHeaderSettings.setBackgroundcolorOne(backgroundcolorOne);
                                                                        } else if (headerSettingsElement.getNodeName().equals("BackgroundcolorTwo")) {
                                                                            String backgroundcolorTwo = XMLHelper.getCharacterDataFromElement(headerSettingsElement);
                                                                            subContainerHeaderSettings.setBackgroundcolorTwo(backgroundcolorTwo);
                                                                        }
                                                                    }
                                                                    subContainerSettings.setSubContainerHeaderSettings(subContainerHeaderSettings);
                                                                }else if (tableSettingsElement.getNodeName().equals("BodySettings")) {
                                                                    NodeList subContainerbodySettingsChildList = tableSettingsElement.getChildNodes();
                                                                    SubContainerBodySettings subContainerBodySettings = new SubContainerBodySettings();
                                                                    for (int bodySettingsCnt = 0; bodySettingsCnt < subContainerbodySettingsChildList.getLength(); bodySettingsCnt++) {
                                                                        Element subContainerBodySettingsElement = (Element) subContainerbodySettingsChildList.item(bodySettingsCnt);
                                                                        if (subContainerBodySettingsElement.getNodeName().equals("Height")) {
                                                                            String height = XMLHelper.getCharacterDataFromElement(subContainerBodySettingsElement);
                                                                            subContainerBodySettings.setHeight(Integer.parseInt(height));
                                                                        } else if (subContainerBodySettingsElement.getNodeName().equals("TextStyle")) {
                                                                            String textStyle = XMLHelper.getCharacterDataFromElement(subContainerBodySettingsElement);
                                                                            subContainerBodySettings.setTextStyle(textStyle);
                                                                        } else if (subContainerBodySettingsElement.getNodeName().equals("TextSize")) {
                                                                            String textSize = XMLHelper.getCharacterDataFromElement(subContainerBodySettingsElement);
                                                                            subContainerBodySettings.setTextSize(Integer.parseInt(textSize));
                                                                        } else if (subContainerBodySettingsElement.getNodeName().equals("TextColor")) {
                                                                            String textColor = XMLHelper.getCharacterDataFromElement(subContainerBodySettingsElement);
                                                                            subContainerBodySettings.setTextColor(textColor);
                                                                        } else if (subContainerBodySettingsElement.getNodeName().equals("Alignment")) {
                                                                            String alignment = XMLHelper.getCharacterDataFromElement(subContainerBodySettingsElement);
                                                                            subContainerBodySettings.setAlignment(alignment);
                                                                        } else if (subContainerBodySettingsElement.getNodeName().equals("BackgroundcolorType")) {
                                                                            String backgroundcolorType = XMLHelper.getCharacterDataFromElement(subContainerBodySettingsElement);
                                                                            subContainerBodySettings.setBackgroundcolorType(backgroundcolorType);
                                                                        } else if (subContainerBodySettingsElement.getNodeName().equals("BackgroundcolorOne")) {
                                                                            String backgroundcolorOne = XMLHelper.getCharacterDataFromElement(subContainerBodySettingsElement);
                                                                            subContainerBodySettings.setBackgroundcolorOne(backgroundcolorOne);
                                                                        } else if (subContainerBodySettingsElement.getNodeName().equals("BackgroundcolorTwo")) {
                                                                            String backgroundcolorTwo = XMLHelper.getCharacterDataFromElement(subContainerBodySettingsElement);
                                                                            subContainerBodySettings.setBackgroundcolorTwo(backgroundcolorTwo);
                                                                        } else if (subContainerBodySettingsElement.getNodeName().equals("BorderColor")) {
                                                                            String borderColor = XMLHelper.getCharacterDataFromElement(subContainerBodySettingsElement);
                                                                            subContainerBodySettings.setBorderColor(borderColor);
                                                                        }
                                                                    }
                                                                    subContainerSettings.setSubContainerBodySettings(subContainerBodySettings);
                                                                }
                                                            }
                                                        }
                                                    }
                                                    subContainerSettingsList.add(subContainerSettings);
                                                }dataManagementOptions.getDetailedPageDetails().getBodySettings().getLeftLayoutSettings().setSubContainerSettings(subContainerSettingsList);}
                                        }
                                    }else  if (bodySettingsElement.getNodeName().equals("RightLayoutSettings")) {
                                        dataManagementOptions.getDetailedPageDetails().getBodySettings().getRightLayoutSettings().setRightLayout(Boolean.parseBoolean(bodySettingsElement.getAttribute("isRightLayout")));
                                        NodeList rightLayoutChildList = bodySettingsElement.getChildNodes();
                                        for (int rightLayoutChildListCnt = 0; rightLayoutChildListCnt < rightLayoutChildList.getLength(); rightLayoutChildListCnt++) {
                                            Element tableColumnsChildElement = (Element) rightLayoutChildList.item(rightLayoutChildListCnt);
                                            NodeList tableColumnsChildList = tableColumnsChildElement.getChildNodes();
                                            List<String> tableColumns = new ArrayList<>();
                                            for (int childCnt = 0; childCnt < tableColumnsChildList.getLength(); childCnt++) {
                                                Element settingFieldsElement = (Element) tableColumnsChildList.item(childCnt);
                                                if (settingFieldsElement.getNodeName().equals("ColumnName")) {
                                                    String TableColumnName = getCharacterDataFromElement(settingFieldsElement);
                                                    tableColumns.add(TableColumnName);
                                                }
                                            }
                                            dataManagementOptions.getDetailedPageDetails().getBodySettings().getRightLayoutSettings().setTableColumns(tableColumns);
                                        }}
                                }
                            }
                        }

                    }}} else if (advanceManagementElement.getNodeName().trim().equalsIgnoreCase("EditColumns")) {
                NodeList columnsChildList = advanceManagementElement.getChildNodes();
                List<EditOrViewColumns> editOrViewColumnsList = new ArrayList<>();
                for (int childCnt = 0; childCnt < columnsChildList.getLength(); childCnt++) {
                    EditOrViewColumns editOrViewColumns = new EditOrViewColumns();
                    Element settingFieldsElement = (Element) columnsChildList.item(childCnt);
                    if (settingFieldsElement.getNodeName().equals("ColumnName")) {
                        String TableColumnName = getCharacterDataFromElement(settingFieldsElement);
                        editOrViewColumns.setColumnName(TableColumnName);
                    }
                    editOrViewColumns.setColumnType(settingFieldsElement.getAttribute("type"));
                    editOrViewColumns.setFormName(settingFieldsElement.getAttribute("subformName"));
                    editOrViewColumnsList.add(editOrViewColumns);
                }
                dataManagementOptions.setEditColumns(editOrViewColumnsList);

            } else if (advanceManagementElement.getNodeName().equals("EnableViewData")) {
                String enableViewData = XMLHelper.getCharacterDataFromElement(advanceManagementElement);
                dataManagementOptions.setEnableViewData(Boolean.parseBoolean(enableViewData));
            } else if (advanceManagementElement.getNodeName().equalsIgnoreCase("IndexPageColumnsOrder")) {
                String indexPageColumnsOrder = XMLHelper.getCharacterDataFromElement(advanceManagementElement);
                dataManagementOptions.setIndexPageColumnsOrder(indexPageColumnsOrder);
            } else if (advanceManagementElement.getNodeName().equalsIgnoreCase("Order")) {
                String order = XMLHelper.getCharacterDataFromElement(advanceManagementElement);
                dataManagementOptions.setOrder(order);
            } else if (advanceManagementElement.getNodeName().equals("EnableEditData")) {
                String enableEditData = XMLHelper.getCharacterDataFromElement(advanceManagementElement);

                dataManagementOptions.setEnableEditData(Boolean.parseBoolean(enableEditData));
            } else if (advanceManagementElement.getNodeName().equals("EnableDeleteData")) {
                String enableDeleteData = XMLHelper.getCharacterDataFromElement(advanceManagementElement);
                dataManagementOptions.setEnableDeleteData(Boolean.parseBoolean(enableDeleteData));
            } else if (advanceManagementElement.getNodeName().equals("SkipIndexPage")) {
                String skipIndexPage = XMLHelper.getCharacterDataFromElement(advanceManagementElement);
                dataManagementOptions.setSkipIndexPage(Boolean.parseBoolean(skipIndexPage));
            } else if (advanceManagementElement.getNodeName().equals("RecordInsertionType")) {
                String allowOnlyOneRecord = XMLHelper.getCharacterDataFromElement(advanceManagementElement);
                dataManagementOptions.setRecordInsertionType(allowOnlyOneRecord);
            } else if (advanceManagementElement.getNodeName().equals("LazyLoading")) {
                String lazyLoading = XMLHelper.getCharacterDataFromElement(advanceManagementElement);
                dataManagementOptions.setLazyLoadingEnabled(Boolean.parseBoolean(lazyLoading));
            } else if (advanceManagementElement.getNodeName().equals("LazyLoadingThreshold")) {
                if (dataManagementOptions.isLazyLoadingEnabled()) {
                    String lazyLoadingThreshold = XMLHelper.getCharacterDataFromElement(advanceManagementElement);
                    dataManagementOptions.setLazyLoadingThreshold(lazyLoadingThreshold);
                }
            } else if (advanceManagementElement.getNodeName().equals("CaptionForAdd")) {
                String captionForAdd = XMLHelper.getCharacterDataFromElement(advanceManagementElement);
                dataManagementOptions.setCaptionForAdd(captionForAdd);

            } else if (advanceManagementElement.getNodeName().equals("FetchData")) {
                String fetchData = XMLHelper.getCharacterDataFromElement(advanceManagementElement);
                dataManagementOptions.setFetchData(fetchData);
            } else if (advanceManagementElement.getNodeName().trim().equalsIgnoreCase("InputParameters")) {
                dataManagementOptions.setFilterColumns(filterColumns((Element) nNode));
            } else if (advanceManagementElement.getNodeName().equals("ReportDisplayType")) {
                String ReportDisplayType = getCharacterDataFromElement(advanceManagementElement);
                if (ReportDisplayType != null && ReportDisplayType != "" && !ReportDisplayType.equalsIgnoreCase("false")) {
                    dataManagementOptions.setReportDisplayType(Integer.parseInt(ReportDisplayType));
                }
            }
        }
        return dataManagementOptions;
    }

    private static List<API_InputParam_Bean> filterColumns(Element actionGroupElement) {
        List<API_InputParam_Bean> inputParam_beans = new ArrayList<>();
        NodeList formFieldsChildList = actionGroupElement.getChildNodes();

        for (int FieldsCnt = 0; FieldsCnt < formFieldsChildList.getLength(); FieldsCnt++) {
            Element fieldsElement = (Element) formFieldsChildList.item(FieldsCnt);
            if (fieldsElement.getNodeName().equalsIgnoreCase("InputParameters")) {

                NodeList inputParametersList = fieldsElement.getChildNodes();

                for (int inputParamCnt = 0; inputParamCnt < inputParametersList.getLength(); inputParamCnt++) {
                    Element inputParamElement = (Element) inputParametersList.item(inputParamCnt);

                    NodeList paramOneList = inputParamElement.getElementsByTagName("Param");
                    Element paramOne = (Element) paramOneList.item(0);

                    String paramOneValue = getCharacterDataFromElement(paramOne);
                    API_InputParam_Bean apiInputParamBean = new API_InputParam_Bean(paramOneValue, "", "");
                    apiInputParamBean.setInParam_Static(paramOne.getAttribute("static"));
                    apiInputParamBean.setInParam_Optional(paramOne.getAttribute("optional"));
                    apiInputParamBean.setInParam_InputMode(paramOne.getAttribute("inputMode"));
                    apiInputParamBean.setEnable(Boolean.parseBoolean(paramOne.getAttribute("enable")));

                    if (apiInputParamBean.isEnable()) {
                        NodeList paramTwoList = inputParamElement.getElementsByTagName("Param");
                        Element paramTwo = (Element) paramTwoList.item(1);

                        String nameAtt = paramTwo.getAttribute("name");

                        if (paramOne.getAttribute("static").equalsIgnoreCase("Yes")) {
                            apiInputParamBean.setEnable(true);
                            apiInputParamBean.setInParam_MappedControl_Type("Static");
                            apiInputParamBean.setInParam_Mapped_ID(getCharacterDataFromElement(paramTwo));
                        } else if (nameAtt.equalsIgnoreCase("expression")) {
                            apiInputParamBean.setEnable(true);
                            apiInputParamBean.setInParam_ExpressionExists(true);
                            apiInputParamBean.setInParam_ExpressionValue(getCharacterDataFromElement(paramTwo));
                        } else if (!nameAtt.equalsIgnoreCase("") &&
                                !nameAtt.equalsIgnoreCase("none")) {
                            apiInputParamBean.setEnable(true);
                            apiInputParamBean.setInParam_MappedControl_Type(nameAtt);
                            apiInputParamBean.setInParam_Mapped_ID(getCharacterDataFromElement(paramTwo));
                        } else {
//                            apiInputParamBean.setEnable(false);
                            apiInputParamBean.setInParam_Mapped_ID(getCharacterDataFromElement(paramTwo));
                            apiInputParamBean.setEnable(Boolean.parseBoolean(paramOne.getAttribute("enable")));
                        }

                        NodeList paramThreeList = inputParamElement.getElementsByTagName("Param");
                        Element paramThree = (Element) paramThreeList.item(2);
                        apiInputParamBean.setInParam_Operator(getCharacterDataFromElement(paramThree));


                        NodeList paramfourList = inputParamElement.getElementsByTagName("Param");
                        Element paramFour = (Element) paramfourList.item(3);
                        apiInputParamBean.setInParam_and_or(getCharacterDataFromElement(paramFour));

                        NodeList paramFiveList = inputParamElement.getElementsByTagName("Param");
                        Element paramFive = (Element) paramFiveList.item(4);
                        apiInputParamBean.setInParam_near_by_distance(getCharacterDataFromElement(paramFive));

                        NodeList paramSixList = inputParamElement.getElementsByTagName("Param");
                        Element paramSix = (Element) paramSixList.item(5);
                        apiInputParamBean.setInParam_near_by_records(getCharacterDataFromElement(paramSix));


                    }

                    inputParam_beans.add(apiInputParamBean);
                }


            }
        }
        return inputParam_beans;
    }

    private static List<API_InputParam_Bean> filterColumns1(Element actionGroupElement) {
        List<API_InputParam_Bean> inputParam_beans = new ArrayList<>();

        try {
            NodeList formFieldsChildList = actionGroupElement.getChildNodes();

            for (int FieldsCnt = 0; FieldsCnt < formFieldsChildList.getLength(); FieldsCnt++) {
                Element fieldsElement = (Element) formFieldsChildList.item(FieldsCnt);
                if (fieldsElement.getNodeName().equalsIgnoreCase("InputParameter")) {

                    NodeList inputParametersList = fieldsElement.getChildNodes();

                    for (int inputParamCnt = 0; inputParamCnt < inputParametersList.getLength(); inputParamCnt++) {
                        Element inputParamElement = (Element) inputParametersList.item(inputParamCnt);

                        NodeList paramOneList = inputParamElement.getElementsByTagName("Param");
                        Element paramOne = (Element) paramOneList.item(0);

                        NodeList visibilityManagementChildList = inputParamElement.getChildNodes();
                        String paramOneValue = visibilityManagementChildList.item(0).getTextContent().trim();


//                            String paramOneValue = getCharacterDataFromElement(paramOne);
                        API_InputParam_Bean apiInputParamBean = new API_InputParam_Bean(paramOneValue, "", "");
                        apiInputParamBean.setInParam_Static(inputParamElement.getAttribute("static"));
                        apiInputParamBean.setInParam_Optional(inputParamElement.getAttribute("optional"));
                        apiInputParamBean.setInParam_InputMode(inputParamElement.getAttribute("inputMode"));
                        apiInputParamBean.setEnable(Boolean.parseBoolean(inputParamElement.getAttribute("enable")));

                        if (apiInputParamBean.isEnable()) {
                            NodeList paramTwoList = inputParamElement.getElementsByTagName("Param");
                            Element paramTwo = (Element) paramTwoList.item(1);

                            String nameAtt = paramTwo.getAttribute("name");

                            if (paramOne.getAttribute("static").equalsIgnoreCase("Yes")) {
                                apiInputParamBean.setEnable(true);
                                apiInputParamBean.setInParam_MappedControl_Type("Static");
                                apiInputParamBean.setInParam_Mapped_ID(getCharacterDataFromElement(paramTwo));
                            } else if (nameAtt.equalsIgnoreCase("expression")) {
                                apiInputParamBean.setEnable(true);
                                apiInputParamBean.setInParam_ExpressionExists(true);
                                apiInputParamBean.setInParam_ExpressionValue(getCharacterDataFromElement(paramTwo));
                            } else if (!nameAtt.equalsIgnoreCase("") &&
                                    !nameAtt.equalsIgnoreCase("none")) {
                                apiInputParamBean.setEnable(true);
                                apiInputParamBean.setInParam_MappedControl_Type(nameAtt);
                                apiInputParamBean.setInParam_Mapped_ID(getCharacterDataFromElement(paramTwo));
                            } else {
                                //                            apiInputParamBean.setEnable(false);
                                apiInputParamBean.setInParam_Mapped_ID(getCharacterDataFromElement(paramTwo));
                                apiInputParamBean.setEnable(Boolean.parseBoolean(paramOne.getAttribute("enable")));
                            }

                            NodeList paramThreeList = inputParamElement.getElementsByTagName("Param");
                            Element paramThree = (Element) paramThreeList.item(2);
                            apiInputParamBean.setInParam_Operator(getCharacterDataFromElement(paramThree));


                            NodeList paramfourList = inputParamElement.getElementsByTagName("Param");
                            Element paramFour = (Element) paramfourList.item(3);
                            apiInputParamBean.setInParam_and_or(getCharacterDataFromElement(paramFour));

                            NodeList paramFiveList = inputParamElement.getElementsByTagName("Param");
                            Element paramFive = (Element) paramFiveList.item(4);
                            apiInputParamBean.setInParam_near_by_distance(getCharacterDataFromElement(paramFive));

                            NodeList paramSixList = inputParamElement.getElementsByTagName("Param");
                            Element paramSix = (Element) paramSixList.item(5);
                            apiInputParamBean.setInParam_near_by_records(getCharacterDataFromElement(paramSix));


                        }

                        inputParam_beans.add(apiInputParamBean);
                    }


                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return inputParam_beans;
    }

    public static VisibilityManagementOptions getControlVisibility(String xmlDesignFormat) {
        VisibilityManagementOptions visibilityManagementOptions = new VisibilityManagementOptions();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(xmlDesignFormat)));
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getChildNodes().item(0).getChildNodes();
            for (int i = 0; i < nList.getLength(); i++) {
                Node nNode = nList.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    if (nNode.getNodeName().trim().equalsIgnoreCase("VisibilityManagement")) {
                        NodeList visibilityManagementChildList = nNode.getChildNodes();
                        for (int visibilityManagementCnt = 0; visibilityManagementCnt < visibilityManagementChildList.getLength(); visibilityManagementCnt++) {
                            Element advanceManagementElement = (Element) visibilityManagementChildList.item(visibilityManagementCnt);
                            if (advanceManagementElement.getNodeName().trim().equalsIgnoreCase("VisibilityOn")) {
                                NodeList columnsChildList = advanceManagementElement.getChildNodes();
                                List<String> visibilityOnColumnsList = new ArrayList<>();
                                for (int childCnt = 0; childCnt < columnsChildList.getLength(); childCnt++) {
                                    Element settingFieldsElement = (Element) columnsChildList.item(childCnt);
                                    if (settingFieldsElement.getNodeName().equals("ColumnName")) {
                                        String TableColumnName = getCharacterDataFromElement(settingFieldsElement);
                                        visibilityOnColumnsList.add(TableColumnName);
                                    }
                                }
                                visibilityManagementOptions.setVisibilityOnColumns(visibilityOnColumnsList);
                            } else if (advanceManagementElement.getNodeName().trim().equalsIgnoreCase("VisibilityOff")) {
                                NodeList columnNodesList = advanceManagementElement.getChildNodes();
                                List<String> columnsList = new ArrayList<>();
                                for (int j = 0; j < columnNodesList.getLength(); j++) {
                                    Node columnNode = columnNodesList.item(j);
                                    if (columnNode.getNodeType() == Node.ELEMENT_NODE && columnNode.getNodeName().trim().equalsIgnoreCase("ColumnName")) {
                                        columnsList.add(columnNode.getTextContent().trim());
                                    }
                                }
                                visibilityManagementOptions.setVisibilityOffColumns(columnsList);
                            } else if (advanceManagementElement.getNodeName().trim().equalsIgnoreCase("Enable")) {
                                NodeList columnNodesList = advanceManagementElement.getChildNodes();
                                List<String> columnsList = new ArrayList<>();
                                for (int j = 0; j < columnNodesList.getLength(); j++) {
                                    Node columnNode = columnNodesList.item(j);
                                    if (columnNode.getNodeType() == Node.ELEMENT_NODE && columnNode.getNodeName().trim().equalsIgnoreCase("ColumnName")) {
                                        columnsList.add(columnNode.getTextContent().trim());
                                    }
                                }
                                visibilityManagementOptions.setEnableColumns(columnsList);
                            } else if (advanceManagementElement.getNodeName().trim().equalsIgnoreCase("Disable")) {
                                NodeList columnNodesList = advanceManagementElement.getChildNodes();
                                List<String> columnsList = new ArrayList<>();
                                for (int j = 0; j < columnNodesList.getLength(); j++) {
                                    Node columnNode = columnNodesList.item(j);
                                    if (columnNode.getNodeType() == Node.ELEMENT_NODE && columnNode.getNodeName().trim().equalsIgnoreCase("ColumnName")) {
                                        columnsList.add(columnNode.getTextContent().trim());
                                    }
                                }
                                visibilityManagementOptions.setDisableColumns(columnsList);
                            }
                        }
                        break;
                    }
                }

            }
        } catch (Exception e) {
            System.out.println("Exception:AdvanceManagement in" + ":" + e.getMessage());
            visibilityManagementOptions = null;
        }
        return visibilityManagementOptions;
    }
    public static VisibilityManagementOptions getControlVisibility(Node  nNode) {
        VisibilityManagementOptions visibilityManagementOptions = new VisibilityManagementOptions();
        try {
                        NodeList visibilityManagementChildList = nNode.getChildNodes();
                        for (int visibilityManagementCnt = 0; visibilityManagementCnt < visibilityManagementChildList.getLength(); visibilityManagementCnt++) {
                            Element advanceManagementElement = (Element) visibilityManagementChildList.item(visibilityManagementCnt);
                            if (advanceManagementElement.getNodeName().trim().equalsIgnoreCase("VisibilityOn")) {
                                NodeList columnsChildList = advanceManagementElement.getChildNodes();
                                List<String> visibilityOnColumnsList = new ArrayList<>();
                                for (int childCnt = 0; childCnt < columnsChildList.getLength(); childCnt++) {
                                    Element settingFieldsElement = (Element) columnsChildList.item(childCnt);
                                    if (settingFieldsElement.getNodeName().equals("ColumnName")) {
                                        String TableColumnName = getCharacterDataFromElement(settingFieldsElement);
                                        visibilityOnColumnsList.add(TableColumnName);
                                    }
                                }
                                visibilityManagementOptions.setVisibilityOnColumns(visibilityOnColumnsList);
                            } else if (advanceManagementElement.getNodeName().trim().equalsIgnoreCase("VisibilityOff")) {
                                NodeList columnNodesList = advanceManagementElement.getChildNodes();
                                List<String> columnsList = new ArrayList<>();
                                for (int j = 0; j < columnNodesList.getLength(); j++) {
                                    Node columnNode = columnNodesList.item(j);
                                    if (columnNode.getNodeType() == Node.ELEMENT_NODE && columnNode.getNodeName().trim().equalsIgnoreCase("ColumnName")) {
                                        columnsList.add(columnNode.getTextContent().trim());
                                    }
                                }
                                visibilityManagementOptions.setVisibilityOffColumns(columnsList);
                            } else if (advanceManagementElement.getNodeName().trim().equalsIgnoreCase("Enable")) {
                                NodeList columnNodesList = advanceManagementElement.getChildNodes();
                                List<String> columnsList = new ArrayList<>();
                                for (int j = 0; j < columnNodesList.getLength(); j++) {
                                    Node columnNode = columnNodesList.item(j);
                                    if (columnNode.getNodeType() == Node.ELEMENT_NODE && columnNode.getNodeName().trim().equalsIgnoreCase("ColumnName")) {
                                        columnsList.add(columnNode.getTextContent().trim());
                                    }
                                }
                                visibilityManagementOptions.setEnableColumns(columnsList);
                            } else if (advanceManagementElement.getNodeName().trim().equalsIgnoreCase("Disable")) {
                                NodeList columnNodesList = advanceManagementElement.getChildNodes();
                                List<String> columnsList = new ArrayList<>();
                                for (int j = 0; j < columnNodesList.getLength(); j++) {
                                    Node columnNode = columnNodesList.item(j);
                                    if (columnNode.getNodeType() == Node.ELEMENT_NODE && columnNode.getNodeName().trim().equalsIgnoreCase("ColumnName")) {
                                        columnsList.add(columnNode.getTextContent().trim());
                                    }
                                }
                                visibilityManagementOptions.setDisableColumns(columnsList);
                            }
                        }
        } catch (Exception e) {
            System.out.println("Exception:AdvanceManagement in" + ":" + e.getMessage());
            visibilityManagementOptions = null;
        }
        return visibilityManagementOptions;
    }

    public static FormLevelTranslation getFormLevelRTL(String xml) {
        FormLevelTranslation formLevelTranslation = new FormLevelTranslation();
        LinkedHashMap<String, String> headerMap = new LinkedHashMap<>();
        LinkedHashMap<String, String> appNamesMap = new LinkedHashMap<>();
        LinkedHashMap<String, String> appDescriptionMap = new LinkedHashMap<>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        Document doc;
        try {
            builder = factory.newDocumentBuilder();
            doc = builder.parse(new InputSource(new StringReader(xml)));
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getChildNodes().item(0).getChildNodes();
            for (int i = 0; i < nList.getLength(); i++) {
                Node nNode = nList.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    if (nNode.getNodeName().trim().equalsIgnoreCase("FormControls")) {
                        NodeList controlNodesList = nNode.getChildNodes();
                        for (int j = 0; j < controlNodesList.getLength(); j++) {
                            Node controlNode = controlNodesList.item(j);
                            if (controlNode.getNodeType() == Node.ELEMENT_NODE && controlNode.getNodeName().contentEquals("RTL")) {
                                NodeList languageNodes = controlNode.getChildNodes();
                                if (languageNodes != null && languageNodes.getLength() > 0) {
                                    for (int k = 0; k < languageNodes.getLength(); k++) {
                                        Node language = languageNodes.item(k);
                                        String langCode = ((Element) language).getAttribute("code").trim();
                                        NodeList innerNodeList = language.getChildNodes();
                                        for (int l = 0; l < innerNodeList.getLength(); l++) {
                                            Node iNode = language.getChildNodes().item(l);
                                            switch (iNode.getNodeName().trim()) {
                                                case "HeaderName":
                                                    headerMap.put(langCode, iNode.getTextContent().trim());
                                                    break;
                                                case "AppName":
                                                    appNamesMap.put(langCode, iNode.getTextContent().trim());
                                                    break;
                                                case "AppDescription":
                                                    appDescriptionMap.put(langCode, iNode.getTextContent().trim());
                                                    break;
                                            }
                                        }
                                    }
                                    formLevelTranslation.setTranslatedAppNames(appNamesMap);
                                    formLevelTranslation.setTranslatedAppDescriptions(appDescriptionMap);
                                    formLevelTranslation.setTranslatedAppTitleMap(headerMap);
                                } else {
                                    formLevelTranslation.setNoTranslationsExist(true);
                                }

                            }
                        }


                    }
                }
            }
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }

        return formLevelTranslation;
    }

    public static String getCharacterDataFromElement(Element e) {
        try {
            Node child = e.getFirstChild();

            if (child instanceof CharacterData) {
                CharacterData cd = (CharacterData) child;
                return cd.getData();
            }
        } catch (Exception exception) {
//            Log.d(TAG, "getCharacterDataFromElement: "+exception.toString());
        }
        return "";
    }

    private static void setTableSettingsObject(DataManagementOptions dataManagementOptions, Node ControlNode) {

        if (((Element) ControlNode).getAttribute("option").trim().equalsIgnoreCase("new")) {
            dataManagementOptions.setSubFormInMainForm(((Element) ControlNode).getAttribute("subFormInMainForm").trim());

            if (ControlNode.getNodeType() == Node.ELEMENT_NODE) {
                Element TableColumnsElement = (Element) ControlNode;

                for (int k = 0; k < TableColumnsElement.getChildNodes().getLength(); k++) {

                    NodeList nNodeChildNodes = TableColumnsElement.getChildNodes();
                    List<String> TableColumnNames = new ArrayList<>();
                    for (int inNodeChildNodes = 0; inNodeChildNodes < nNodeChildNodes.getLength(); inNodeChildNodes++) {
                        Node nNodeTableColumn = nNodeChildNodes.item(inNodeChildNodes);
                        if (nNodeChildNodes.item(inNodeChildNodes).getNodeType() == Node.ELEMENT_NODE) {
                            Element settingFieldsElement = (Element) nNodeChildNodes.item(inNodeChildNodes);
                            if (settingFieldsElement.getNodeName().equals("TableColumns")) {
                                NodeList settingsChildList = nNodeTableColumn.getChildNodes();

                                for (int settingsFieldsCnt = 0; settingsFieldsCnt < settingsChildList.getLength(); settingsFieldsCnt++) {
                                    if (settingsChildList.item(settingsFieldsCnt).getNodeType() == Node.ELEMENT_NODE) {
                                        Element ColumnNameElement = (Element) settingsChildList.item(settingsFieldsCnt);

                                        if (ColumnNameElement.getNodeName().equals("ColumnName")) {
                                            String TableColumnName = getCharacterDataFromElement(ColumnNameElement);

                                            TableColumnNames.add(TableColumnName);
                                        }
                                    }
                                }

                                dataManagementOptions.setList_Table_Columns(TableColumnNames);

                            }

                        }
                    }
                }
            }
        }
    }

    public static String getSubFormInMainForm(String xmlDesignFormat) {
        try {
            if (xmlDesignFormat.startsWith("N")) {
                xmlDesignFormat = xmlDesignFormat.substring(1);
            }
            xmlDesignFormat = xmlDesignFormat.replaceAll("&(?!amp;)", "&amp;");
            xmlDesignFormat = xmlDesignFormat.replaceAll("CNAME", "CDATA");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(xmlDesignFormat)));
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getChildNodes().item(0).getChildNodes();
            for (int i = 0; i < nList.getLength(); i++) {
                Node nNode = nList.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    if (nNode.getNodeName().equalsIgnoreCase("TableSettings")) {
                        NodeList ControlNodeList = nNode.getChildNodes();
                        if (ControlNodeList.getLength() > 0) {
                            for (int j = 0; j < ControlNodeList.getLength(); j++) {
                                Node ControlNode = ControlNodeList.item(j);
                                if (ControlNode.getNodeName().equalsIgnoreCase("MainTableSettings")) {
                                    return ((Element) ControlNode).getAttribute("subFormInMainForm").trim();
                                }

                            }
                        }
                    }
                }

            }
        } catch (Exception e) {
            System.out.println("Exception:AdvanceManagement in" + ":" + e.getMessage());

        }
        return "";
    }

    public static List<String> getList_Table_Columns(String xmlDesignFormat) {
        List<String> tableColumns = new ArrayList<>();
        try {
            if (xmlDesignFormat.startsWith("N")) {
                xmlDesignFormat = xmlDesignFormat.substring(1);
            }
            xmlDesignFormat = xmlDesignFormat.replaceAll("&(?!amp;)", "&amp;");
            xmlDesignFormat = xmlDesignFormat.replaceAll("CNAME", "CDATA");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(xmlDesignFormat)));
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getChildNodes().item(0).getChildNodes();
            for (int i = 0; i < nList.getLength(); i++) {
                Node nNode = nList.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    if (nNode.getNodeName().equalsIgnoreCase("TableSettings")) {
                        NodeList ControlNodeList = nNode.getChildNodes();
                        if (ControlNodeList.getLength() > 0) {
                            for (int j = 0; j < ControlNodeList.getLength(); j++) {
                                Node ControlNode = ControlNodeList.item(j);
                                if (ControlNode.getNodeName().equalsIgnoreCase("MainTableSettings")) {
                                    if (((Element) ControlNode).getAttribute("option").trim().equalsIgnoreCase("new")) {


                                        if (ControlNode.getNodeType() == Node.ELEMENT_NODE) {
                                            Element TableColumnsElement = (Element) ControlNode;

                                            for (int k = 0; k < TableColumnsElement.getChildNodes().getLength(); k++) {

                                                NodeList nNodeChildNodes = TableColumnsElement.getChildNodes();
                                                List<String> TableColumnNames = new ArrayList<>();
                                                for (int inNodeChildNodes = 0; inNodeChildNodes < nNodeChildNodes.getLength(); inNodeChildNodes++) {
                                                    Node nNodeTableColumn = nNodeChildNodes.item(inNodeChildNodes);
                                                    if (nNodeChildNodes.item(inNodeChildNodes).getNodeType() == Node.ELEMENT_NODE) {
                                                        Element settingFieldsElement = (Element) nNodeChildNodes.item(inNodeChildNodes);
                                                        if (settingFieldsElement.getNodeName().equals("TableColumns")) {
                                                            NodeList settingsChildList = nNodeTableColumn.getChildNodes();

                                                            for (int settingsFieldsCnt = 0; settingsFieldsCnt < settingsChildList.getLength(); settingsFieldsCnt++) {
                                                                if (settingsChildList.item(settingsFieldsCnt).getNodeType() == Node.ELEMENT_NODE) {
                                                                    Element ColumnNameElement = (Element) settingsChildList.item(settingsFieldsCnt);

                                                                    if (ColumnNameElement.getNodeName().equals("ColumnName")) {
                                                                        String TableColumnName = getCharacterDataFromElement(ColumnNameElement);

                                                                        TableColumnNames.add(TableColumnName);
                                                                    }
                                                                }
                                                            }

                                                            return TableColumnNames;

                                                        }

                                                    }
                                                }
                                            }
                                        }
                                    }
                                }

                            }
                        }
                    }
                }

            }
        } catch (Exception e) {
            System.out.println("Exception:AdvanceManagement in" + ":" + e.getMessage());

        }
        return tableColumns;
    }
}

