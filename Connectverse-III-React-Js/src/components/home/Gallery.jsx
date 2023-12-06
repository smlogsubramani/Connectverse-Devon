import JsonData from "../../data/index.json";
import Image from "./Image";

const Gallery = () => {
  return (
    <div id='portfolio' className='text-center'>
      <div className='container'>
        <div className='section-title'>
          <h2>Gallery</h2>
          <p>
            Lorem ipsum dolor sit amet, consectetur adipiscing elit duis sed
            dapibus leonec.
          </p>
        </div>
        
        <div className='row'>
          <div className='portfolio-items'>

            {/* <div class="row">
              <div class="col">
                Column
              </div>
            </div> */}

            <div class="row">
            {JsonData.Gallery
              ? JsonData.Gallery.map((d, i) => (
                // <div key={`${d.title}-${i}`} className='col-sm-6 col-md-4 col-lg-4'>
                //   <Image title={d.title} largeImage={d.largeImage} smallImage={d.smallImage} />
                // </div>

                  <div key={`${d.title}-${i}`} className='col-sm-6 col-md-4 col-lg-4'>
                    <Image title={d.title} largeImage={d.largeImage} smallImage={d.smallImage} />
                  </div>
              ))
              : 'Loading...'}
            </div>

          </div>
        </div>
      </div>
    </div>
  )
}

export default Gallery;
