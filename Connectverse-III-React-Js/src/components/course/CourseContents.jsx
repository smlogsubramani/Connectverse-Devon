import { Clipboard2CheckFill } from "react-bootstrap-icons";
import {
    Accordion,
    AccordionItem,
    AccordionItemHeading,
    AccordionItemPanel,
    AccordionItemButton
} from 'react-accessible-accordion';
import { Tabs, TabPanel } from "react-tabs";


const courseContents = ({ courseContents }) => (
    <div className="faq-area ptb-100">
        <div className="container">
            <div className="tab faq-accordion-tab">
                <Tabs>
                    <TabPanel>
                        <div className="faq-accordion">
                            <Accordion allowZeroExpanded preExpanded={['a']}>

                                {
                                    courseContents.map(content => (
                                        <AccordionItem key={content.id} uuid={content.id}>
                                            <AccordionItemHeading>
                                                <AccordionItemButton>
                                                    <h5>{ content.tittle }</h5>
                                                </AccordionItemButton>
                                            </AccordionItemHeading>
                                            <AccordionItemPanel>
                                                {
                                                    content.subTittles.map(title => (
                                                        <p><Clipboard2CheckFill style={{ color: "green" }}/> { title }</p>
                                                    ))
                                                }
                                            </AccordionItemPanel>
                                         </AccordionItem>
                                    ))
                                }

                             </Accordion>
                         </div>
                    </TabPanel>
                </Tabs>
            </div>
        </div>
    </div>
);

export default courseContents;